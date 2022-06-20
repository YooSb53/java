package 자바;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.Scanner;


public class Project {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        

        //switch 에서는 변수가 공유 되므로 switch문 안에서 변수 선언하지 않고 상단에서 선언한다.

        Connection conn = null;

        PreparedStatement pst = null;
        // sql 구문을 실행한다. 
        
        int menu = 0;
        int student = 0;
        int age = 0;
        String name = "";       
        String phone = "";
        String email = "";
        String search = "";
        String sql = "";

        
        String url = "jdbc:mysql://localhost:3306/odbo2";
        String user = "root";
        String password = "123456";

        
        System.out.println("===============학생관리프로그램===============");

        while(true) {

            System.out.println("1.학생추가 2.전체명단조회 3.특정학생조회 4.학생정보수정 5.학생삭제 6.프로그램종료");

            System.out.print("메뉴 선택 >> ");

            menu = sc.nextInt();

            

            switch(menu) {

                case 1 : //학생등록

                    System.out.println(menu+"번 선택");

                    System.out.println("등록할 학생의 정보를 입력하시오.");
                    
                    System.out.print("이름 : ");
                    name = sc.next();

                    System.out.print("나이 : ");
                    age = sc.nextInt();

                    System.out.print("전화번호 : ");
                    phone = sc.next();

                    System.out.print("이메일 : ");
                    email = sc.next();
                                       

                    try {                     
                        Class.forName("com.mysql.cj.jdbc.Driver");                       

                        //데이터베이스 연결객체( Connection ) 생성                       

                        conn = DriverManager.getConnection(url, user, password);
                        

                        // sql 구문 준비 객체( PreparedStatement ) 생성                       

                        sql = " INSERT INTO student VALUES (?, ?, ?, ?) ";
                        pst = conn.prepareStatement(sql);
                        
                      
                        pst.setString(4,name);
                        pst.setInt(1, age);
                        pst.setString(2, phone);
                        pst.setString(3, email);

                        

                        // sql문 실행하여 결과 처리
                        //executeUpdate : insert, delete, update ( 테이블의 변화가 생길때 사용되는 명령 )

                        int cnt = pst.executeUpdate();

                        if(cnt > 0) {
                            System.out.println("학생추가 성공");

                        } else {
                            System.out.println("학생추가 실패");

                        }

                    //} catch (ClassNotFoundException e) {

                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        try {
                        	
                            pst.close();                          
                            conn.close();

                        } catch (SQLException e) {
                            e.printStackTrace();

                        }

                    }
                   
                    System.out.println();
                    break;

                case 2 : //전체학생 조회

                    System.out.println(menu+"번 선택");

                    //전체 학생 조회
                    System.out.println("===============학생목록===============");
                   
                    try {                       
                        Class.forName("com.mysql.cj.jdbc.Driver");
                                             

                        conn = DriverManager.getConnection(url, user, password);
                       

                        sql = " SELECT * FROM student A ORDER BY name";
                        // sql문을 실행하여 결과 처리
                        
                        pst = conn.prepareStatement(sql);                      
                        
                        ResultSet rs = pst.executeQuery();

                        while(rs.next()) {
                            int row = rs.getRow();
                           
                            name = rs.getString(4);
                          
                            age = rs.getInt(1);
                          
                            phone = rs.getString(2);
                        
                            email = rs.getString(3);                            

                            
                            System.out.println("--------------------------------------------------");         
                            
                            System.out.print("학생이름 : "+name+"\t");
                            
                            System.out.print("학생나이 : "+age+"\t");
                            
                            System.out.print("학생전화번호 : "+phone+"\t");
                            
                            System.out.print("학생이메일 : "+email);
                            
                            System.out.println();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();

                    } finally {
                        try {

                            pst.close();
                            conn.close();

                        } catch (SQLException e) {
                            e.printStackTrace();

                        }

                    }
                    
                    System.out.println();
                    break;

                case 3 : //특정학생조회

                    System.out.println(menu+"번 선택");
                    System.out.print("학생이름 입력 : ");
                    search = sc.next();
                    System.out.println("=============== "+search+" ===============");
                   
                    try {
                       
                        Class.forName("com.mysql.cj.jdbc.Driver");                      

                        conn = DriverManager.getConnection(url, user, password);                                           

                        sql = " SELECT * FROM student WHERE name = ? ";
                        pst = conn.prepareStatement(sql);
                        pst.setString(1, search);

                                           
                        ResultSet rs = pst.executeQuery();
                        boolean isList = false;
                        while(rs.next()) {
                        
                            name = rs.getString("name");

                            age = rs.getInt("age");

                            phone = rs.getString("phone");

                            email = rs.getString("email");                            

                            System.out.print("학생이름 : "+name+"\t");

                            System.out.print("학생나이 : "+age+"\t");

                            System.out.print("학생전화번호 : "+phone+"\t");

                            System.out.print("학생이메일 : "+email);

                            System.out.println();

                            isList = true;

                        }

                        if(isList==false) {

                            System.out.println("검색된 학생이 없습니다.");

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    } finally {

                        try {

                            pst.close();

                            conn.close();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }

                    }

                    

                    System.out.println();

                    break;

                case 4 : //학생정보수정

                    System.out.println(menu+"번 선택");

                    try {
                    	
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    
                        conn = DriverManager.getConnection(url, user, password);
                        
                        System.out.print("변경할 학생이름 : ");
                        name = sc.next();
                                            

                        sql = " SELECT * FROM student WHERE name LIKE ? ";

                        pst = conn.prepareStatement(sql);

                        String names = '%'+name+'%';

                        pst.setString(1, names);

                        ResultSet rs = pst.executeQuery();

                        while(rs.next()) {                            

                            name = rs.getString("name");
                            age = rs.getInt("age");
                            phone = rs.getString("phone");
                            email = rs.getString("email");

                            System.out.println("=============== "+names+" ===============");

                            System.out.println();                           

                            System.out.print("학생이름 : "+name+"\t");

                            System.out.print("학생나이 : "+age+"\t");

                            System.out.print("학생전화번호 : "+phone+"\t");

                            System.out.print("학생이메일 : "+email);

                            System.out.println();
                            System.out.println();

                            System.out.println("=============== ======= ===============");

                            System.out.println();

                        }
                     

                        System.out.print("변경하실 내용 : 선택 [1]이름, [2]나이, [3]연락처, [4]이메일, [5]전체변경 :");

                        int sel = sc.nextInt();

                        if(sel == 1) {

                            System.out.print("변경할 이름 : ");
                            name = sc.next();

                        } else if(sel == 2) {

                            System.out.print("변경할 나이 : ");
                            age = sc.nextInt();

                        } else if(sel == 3) {

                            System.out.print("변경할 연락처 : ");
                            phone = sc.next();

                        } else if(sel == 4) {

                            System.out.print("변경할 이메일 : ");
                            email = sc.next();

                        } else if(sel == 5) {

                            System.out.print("변경할 이름 : ");
                            name = sc.next();

                            System.out.print("변경할 나이 : ");
                            age = sc.nextInt();

                            System.out.print("변경할 연락처 : ");
                            phone = sc.next();

                            System.out.print("변경할 이메일 : ");
                            email = sc.next();

                        }
                                              
                        sql = " UPDATE student ";
                        sql += " SET ";
                        sql += " name = ?, ";
                        sql += " age = ?, ";
                        sql += " phone = ?, ";
                        sql += " email = ? ";
                       
                                            
                        pst = conn.prepareStatement(sql);
                       
                        // 바인드 변수를 채운다.

                        pst.setString(1,name);
                        
                        pst.setInt(2, age);
                        
                        pst.setString(3, phone);

                        pst.setString(4, email);
                      
                        

                        int cnt = pst.executeUpdate();

                        if(cnt > 0) {

                            System.out.println("학생수정 성공");

                        } else {

                            System.out.println("학생수정 실패");

                        }

                    //} catch (ClassNotFoundException e) {

                    } catch (Exception e) {

                        e.printStackTrace();

                    } finally {

                        try {

                            pst.close();

                            conn.close();

                        } catch (SQLException e) {

                            e.printStackTrace();

                        }

                    }

                    System.out.println();

                    break;

                case 5 : //학생정보삭제

                    System.out.println(menu+"번 선택");

                    

                    try {

                        Class.forName("com.mysql.cj.jdbc.Driver");

                        conn = DriverManager.getConnection(url, user, password);

                        

                        System.out.print("삭제할 학생 이름을 입력하세요 : ");

                        name = sc.next();

                        

                        sql = " SELECT * FROM student WHERE name = ? ";

                        pst = conn.prepareStatement(sql);

                        pst.setString(1, name);

                        ResultSet rs = pst.executeQuery();

                        boolean isList = false; 

                        while(rs.next()) {
                            
                            name = rs.getString("name");

                            age = rs.getInt("age");

                            phone = rs.getString("phone");

                            email = rs.getString("email");

                            System.out.println("========= 삭제대상 : "+name+" ===============");

                            System.out.println();
                          

                            System.out.print("학생이름 : "+name+"\t");

                            System.out.print("학생나이 : "+age+"\t");

                            System.out.print("학생전화번호 : "+phone+"\t");

                            System.out.print("학생이메일 : "+email);

                            System.out.println();

                            System.out.println();

                            System.out.println("=============== ======= ===============");

                            System.out.println();

                            isList = true;

                        }

                        if(isList == false) {

                            System.out.println("등록된 대상이 없습니다. ");

                        }

                        

                        sql = " DELETE FROM student WHERE name = ? ";

                        pst = conn.prepareStatement(sql);

                        pst.setString(1, name);

                        

                        int cnt = pst.executeUpdate();

                        if(cnt > 0) {

                            System.out.println(name +" 학생 정보 삭제 ");

                        } else {

                            System.out.println(name + " 학생 정보 삭제 실패 ");

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                    } finally {

                        try {

                            pst.close();

                            conn.close();

                        } catch(SQLException e) {

                            e.printStackTrace();

                        }

                    }

                    

                    System.out.println();

                    break;

                case 6 :  //프로그램 종료

                    System.out.println(menu+"번 선택");               

                    System.out.println();

                    break;

            }           

            if(menu == 6) {

                System.out.println("프로그램종료");

                break;

            }
        }

        sc.close();

    }
}