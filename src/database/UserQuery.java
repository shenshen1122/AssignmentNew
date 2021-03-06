package database;
import app.User;
import app.Video;

import java.sql.*;

public class UserQuery extends Query{
    public static User getUser( int userID) throws Exception{
        // associated with DeleteVideo class, main method
        // obtain the LATEST details of a REQUIRED user from database
        // construct and return the an User object

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query = "SELECT * FROM assignment.user WHERE userID = " + userID + " ; ";
        ResultSet rs = st.executeQuery( query );

        rs.next();

        int    a  = rs.getInt("userID");
        String b  = rs.getString("name");
        String c  = rs.getString("email");
        String d  = rs.getString("password");
        int    e  = rs.getInt("videosCount");
        int    f  = rs.getInt("subscribersCount");
        Video[] g = VideoQuery.getVideos( a );
        st.close();
        con.close();

        return new User(a, b, c, d, e, f, g);
    }
    public static User[] getUsers() throws Exception{
        // returns an array of all users in database

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query = "SELECT * FROM assignment.user;";
        ResultSet rs = st.executeQuery( query );

        int L = getSizeOFResultSet( rs );
        User[] users = new User[L];
//        System.out.println(L);

        for (int i = 0; i < users.length; i++) {
            rs.next();

            int    a  = rs.getInt("userID");
            String b  = rs.getString("name");
            String c  = rs.getString("email");
            String d  = rs.getString("password");
            int    e  = rs.getInt("videosCount");
            int    f  = rs.getInt("subscribersCount");
            Video[] g = VideoQuery.getVideos( a );

            users[i] = new User(a, b, c, d, e, f, g);
        }
        st.close();
        con.close();

        return users;
    }
    public static void insertNew( User u) throws Exception {
        // insert a new record of user

        String query = "INSERT INTO assignment.user VALUES(?,?,?,?,?,?)";
        Connection con = getConnection();
        PreparedStatement pst = con.prepareStatement(query);

        // userID, name, email, password, videosCount, subscribersCount
        pst.setString(1, Integer.toString(u.getUserID()));       // userID
        pst.setString(2, u.getName());            // name
        pst.setString(3, u.getEmail());           // email
        pst.setString(4, u.getPassword());        // password
        pst.setString(5, Integer.toString(u.getVideosCount()));     // videosCount
        pst.setString(6, Integer.toString(u.getSubscribersCount()));// subscribersCount
        pst.executeUpdate();

        pst.close();
        con.close();
    }
    public static void changeEmail(User user) throws Exception{
        Connection con = getConnection();
        Statement st = con.createStatement();

        String query =   "UPDATE assignment.user\n" +
                "SET email = \""+ user.getEmail() + "\"\n" +
                "WHERE userID = "+ user.getUserID()+";";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void changePassword(User user ) throws Exception{
        Connection con = getConnection();
        Statement st = con.createStatement();

        String query =   "UPDATE assignment.user\n" +
                "SET password = \""+ user.getPassword() + "\"\n" +
                "WHERE userID = "+ user.getUserID()+";";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void updateSubscribersCount( int userID ) throws Exception {
        // updates subscribersCount of a user in "user" table

        Connection con = getConnection();
        Statement st = con.createStatement();

        int occ = SubscriberQuery.getSubscribersCount( userID );
        String query =   "UPDATE assignment.user\n" +
                "SET subscribersCount = " + occ + "\n" +
                "WHERE userID = " + userID + " ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
    public static void deleteAcc( int userID ) throws Exception{
        // this method deletes a record from "user" table based on (userID)

        Connection con = getConnection();
        Statement st = con.createStatement();
        String query =   "DELETE \n" +
                "FROM assignment.user\n" +
                "WHERE userID = " + userID +" ; ";

        st.executeUpdate( query );
        st.close();
        con.close();
    }
}

