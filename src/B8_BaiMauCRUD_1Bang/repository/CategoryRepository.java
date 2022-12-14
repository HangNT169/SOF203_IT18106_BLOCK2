/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package B8_BaiMauCRUD_1Bang.repository;

import B8_BaiMauCRUD_1Bang.domainmodel.Category;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author hangnt
 */
public class CategoryRepository {
//    CURD:
//    R: READ <=> SELECT 

    public List<Category> getAll() {
        //  B1: Viet cau query 
        String query = """
                      SELECT id, category_code, category_name
                      FROM hibernate_demo.dbo.category;
                      """;
        //B2: Mo connection 
//        https://github.com/AnhDT0407/Course-JavaCore/blob/master/Java-Lesson/Java%207/try-with-resources.md
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            // B3: Statement : la 1 inteface cho phep 
            //gui cac cau lenh SQL toi Database 
            //   B4: Tao resultset: dai dien cho cac tap hop  cac 
            // record ( ban ghi ) co duoc sau  khi chay sql          
            ResultSet rs = ps.executeQuery();
            // B5: Tao kieu tra ve 
            List<Category> lists = new ArrayList<>();
            // B6: Doc data tu result set 
            while (rs.next()) {
                Category category
                        = new Category(rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3));
                lists.add(category);
            }
            return lists;
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    public Category getOne(Long id) {
        //  B1: Viet cau query 
        String query = """
                      SELECT id, category_code, category_name
                      FROM hibernate_demo.dbo.category
                      WHERE id = ? 
                      """;
        //B2: Mo connection 
//        https://github.com/AnhDT0407/Course-JavaCore/blob/master/Java-Lesson/Java%207/try-with-resources.md
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            // B3: Statement : la 1 inteface cho phep 
            //gui cac cau lenh SQL toi Database 
            //   B4: Tao resultset: dai dien cho cac tap hop  cac 
            // record ( ban ghi ) co duoc sau  khi chay sql     
            ps.setObject(1, id);
            ResultSet rs = ps.executeQuery();
            // B5: Tao kieu tra ve 
            // B6: Doc data tu result set 
            if (rs.next()) {
                Category category
                        = new Category(rs.getLong(1),
                                rs.getString(2),
                                rs.getString(3));
                return category;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return null;
    }

    //C: create

    public boolean add(Category category) {
        int check = 0;
        String query = """
                     INSERT INTO [dbo].[category]
                                ([category_code]
                                ,[category_name])
                          VALUES
                                (?,?)
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, category.getCategoryCode());
            ps.setObject(2, category.getCategoryName());
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean delete(Long id) {
        int check = 0;
        String query = """
                     DELETE FROM [dbo].[category]
                                      WHERE id = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, id);
            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public boolean update(Category category, Long id) {
        int check = 0;
        String query = """
                     UPDATE [dbo].[category]
                        SET [category_code] = ?
                           ,[category_name] = ?
                      WHERE id = ?
                     """;
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, category.getCategoryCode());
            ps.setObject(2, category.getCategoryName());
            ps.setObject(3, id);

            check = ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return check > 0;
    }

    public static void main(String[] args) {
//        List<Category> lists = new CategoryRepository().getAll();
//        for (Category c : lists) {
//            System.out.println(c.toString());
//        }
        Category category = new Category(5L, "Test", "test1");
        boolean add = new CategoryRepository().delete(3L);
        System.out.println(add);

    }
}
