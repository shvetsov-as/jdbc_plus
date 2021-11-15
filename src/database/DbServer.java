/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author User
 */
public class DbServer implements IDbService {

    private static LocalDate todate = LocalDate.now();
    private static String query;
    private static List<Object> list = new ArrayList<>();
    private Statement st;
    private PreparedStatement ps;
    private PreparedStatement ps2;
    private Connector connector = new Connector();

    public void startServer() {
        connector.checkDriver();
        connector.setConnection();
        connector.getCon();
    }

    public void stopServer() {
        connector.closeConnection();
    }

    @Override
    public boolean addAuthor(Author author) throws DocumentException {

        if (author.getAuthor_id() == 0 && author.getAuthor().trim().equals("") && author.getNotes().trim().equals("")) {
            throw new DocumentException("Oshibka v addAuthor(Author author)");
        } else {
            startServer();

            if (author.getAuthor_id() != 0 && !author.getAuthor().equals("") && !author.getNotes().equals("")) {// novyi avtor
                try {
                    query = "INSERT INTO Authors (Author_id, Author, Notes) VALUES(?, ?, ?)";
                    ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("#3 Statement author add sozdan");
                } catch (SQLException ex) {
                    System.out.println("Oshibka TRUE Author ADD " + ex.getMessage());
                }

                try {
                    ps.setInt(1, author.getAuthor_id());
                    ps.setString(2, author.getAuthor());
                    ps.setString(3, author.getNotes());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen " + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka TRUE ps.setInt(1, author.getAuthor_id()); " + ex.getMessage());
                }

                stopServer();
                return true;

            } else if (author.getAuthor_id() != 0 && !author.getAuthor().equals("")) {

                try {
                    query = "UPDATE Authors SET Notes = ? WHERE Author_id = ?";
                    ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);//
                    System.out.println("#3 Statement author update1 sozdan");

                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE ADDAuthor Update" + ex.getMessage());;
                }

                try {
                    ps.setString(1, author.getNotes());
                    ps.setInt(2, author.getAuthor_id());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen " + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE ps.setString(1, author.getNotes()); " + ex.getMessage());
                }
                stopServer();
                return false;

            } else if (author.getAuthor_id() == 0 && !author.getAuthor().equals("")) {

                try {
                    query = "UPDATE Authors SET Notes = ? WHERE Author = ?";
                    ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);//
                    System.out.println("#3 Statement author update2 sozdan");

                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE ADDAuthor Update" + ex.getMessage());;
                }

                try {
                    ps.setString(1, author.getNotes());
                    ps.setString(2, author.getAuthor());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen " + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE ps.setString(2, author.getAuthor()); " + ex.getMessage());
                }
                stopServer();
                return false;

            }

        }
        stopServer();
        throw new DocumentException("Oshibka v zapolnenii poley addAuthor()");
    }

    @Override
    public boolean addDocument(Document doc, Author author) throws DocumentException {
        if (author.getAuthor_id() == 0
                && author.getAuthor().trim().equals("")
                && author.getNotes().trim().equals("")
                && doc.getAuthor_id() == 0
                && doc.getDate() == null
                && doc.getDocument_id() == 0
                && doc.getText().trim().equals("")
                && doc.getTitle().trim().equals("")) {
            throw new DocumentException("Oshibka v addAuthor(Author author)");
        } else {
            startServer();

            if (doc.getDocument_id() != 0
                    && !doc.getTitle().trim().equals("")
                    && doc.getDate() != null
                    && doc.getAuthor_id() != 0
                    && !author.getAuthor().trim().equals("")
                    && author.getAuthor_id() != 0) {// novyi doc
                try {
                    addAuthor(author);
                    startServer();
                    query = "INSERT INTO Documents (document_id, title, text, date, doc_author) VALUES (?, ?, ?, " + "'" + todate + "', " + "?);";
                    ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("#3 addDocument");
                } catch (SQLException ex) {
                    System.out.println("Oshibka TRUE addDocument " + ex.getMessage());
                }

                try {
                    ps.setInt(1, doc.getDocument_id());
                    ps.setString(2, doc.getTitle());
                    ps.setString(3, doc.getText());
                    ps.setInt(4, doc.getAuthor_id());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen " + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka TRUE addDocument ps.setInt(1, doc.getDocument_id()); " + ex.getMessage());
                }

                stopServer();
                return true;

            } else if (doc.getDocument_id() != 0 && doc.getAuthor_id() != 0) {
                try {
                    query = "UPDATE Documents SET title = ?, text = ?" + "'" + todate + "', " + "WHERE document_id = ?";
                    ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("#3 updateDocument");
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE updateDocument " + ex.getMessage());
                }

                try {
                    ps.setString(1, doc.getTitle());
                    ps.setString(2, doc.getText());
                    ps.setInt(3, doc.getDocument_id());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen v DOCUMENTS" + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE updateDocument ps.setString(1, doc.getTitle()); " + ex.getMessage());
                }

                try {
                    query = "UPDATE Authors SET Author = ?, Notes = ? WHERE Author_id = ?";
                    ps2 = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                    System.out.println("#3 updateDocumentAuthor");
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE updateDocumentAuthor " + ex.getMessage());
                }

                try {
                    ps2.setString(1, author.getAuthor());
                    ps2.setString(2, author.getNotes());
                    ps2.setInt(3, author.getAuthor_id());
                    ps.execute();
                    int n = ps.getUpdateCount();
                    System.out.println("Vsego zamen v AUTHOR" + n);
                } catch (SQLException ex) {
                    System.out.println("Oshibka FALSE updateDocument ps.setInt(1, doc.getDocument_id()); " + ex.getMessage());
                }

                stopServer();
                return false;
            }
        }
        stopServer();
        throw new DocumentException("Oshibka obnovleniya");
    }

    @Override
    public Document[] findDocumentByContent(String content) throws DocumentException {

        if (content == null || content.equals("")) {
            throw new DocumentException("ne vernoe znachenie String content");
        }

        try {
            startServer();
            query = "SELECT document_id, title FROM Documents JOIN Authors ON Author_id = Doc_author WHERE title LIKE '%" + content + "%' OR text LIKE '%" + content + "%';";
            ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            System.out.println("#3 findDocumentByContent");
        } catch (SQLException ex) {
            System.out.println("Oshibka findDocumentByContent " + ex.getMessage());
        }

        try {
            boolean b = ps.execute();
            if (b == true) {//esli T - mojem poluchitb ResultSet (v rs)
                ResultSet rs = ps.getResultSet();//executeQuery - esli znaem chto tip komandbI vernet ResultSet
                System.out.println("## polychen ResultSet");
                //obrabotka ResultSet
                printResult(rs);
            }
        } catch (SQLException ex) {
            System.out.println("Oshibka findDocumentByContent ps.execute(); " + ex.getMessage());
        }

        Document[] documents = new Document[list.size()];

        Iterator<Object> it = list.iterator();
        int i = 0;
        if (it.hasNext()) {
            Object obj = it.next();
            documents[i] = (Document) obj;
            i++;
            System.out.println(it.next());
        }
        stopServer();
        return documents;
    }

    @Override
    public boolean deleteAuthor(Author author) throws DocumentException {
        if (author == null || author.getAuthor().equals("")) {
            throw new DocumentException("Oshibka v deleteAuthor(Author author)");
        }
        boolean b = false;

        if (author.getAuthor_id() != 0) {
            try {
                startServer();
                query = "DELETE FROM Documents WHERE doc_author = ?";
                ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setInt(1, author.getAuthor_id());
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) prepSt" + ex.getMessage());
            }

            try {
                ps.execute();
                int n = 0;
                n = ps.getUpdateCount();
                System.out.println("Vsego zamen deleteAuthor" + n);
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) execute" + ex.getMessage());
            }

            query = "DELETE FROM Authors WHERE author_id = ?";

            try {
                ps2 = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps2.setInt(1, author.getAuthor_id());
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) prepSt2" + ex.getMessage());
            }
            try {
                ps2.execute();
                int k = 0;
                k = ps.getUpdateCount();
                System.out.println("Vsego zamen deleteAuthor" + k);
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) execute" + ex.getMessage());
            }
            b = true;
            return b;
        } else {
            try {
                startServer();
                query = "DELETE FROM Documents WHERE doc_author = ?";
                ps = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps.setInt(1, author.getAuthor_id());
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) prepSt#2" + ex.getMessage());
            }

            try {
                ps.execute();
                int n = 0;
                n = ps.getUpdateCount();
                System.out.println("Vsego zamen deleteAuthor" + n);
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) execute" + ex.getMessage());
            }
            query = "DELETE FROM Authors WHERE author = ?";
            try {
                ps2 = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                ps2.setInt(1, author.getAuthor_id());
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) prepSt#3" + ex.getMessage());
            }

            try {
                ps2.execute();
                int k = 0;
                k = ps.getUpdateCount();
                System.out.println("Vsego zamen deleteAuthor" + k);
            } catch (SQLException ex) {
                System.out.println("Oshibka deleteAuthor(Author author) execute" + ex.getMessage());
            }
            stopServer();
            b = true;
            return b;
        }
    }

    @Override
    public boolean deleteAuthor(int id) throws DocumentException {
        if (id == 0) {
            throw new DocumentException("Oshibka v deleteAuthor(int id)");
        }
        boolean b = false;
        try {
            startServer();
            query = "DELETE FROM Documents WHERE doc_author = " + id + ";";
            st = connector.getCon().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        } catch (SQLException ex) {
            System.out.println("Oshibka deleteAuthor(id) St" + ex.getMessage());
        }

        try {
            st.execute(query);
            int n = 0;
            n = st.getUpdateCount();
            System.out.println("Vsego zamen deleteAuthor id" + n);
        } catch (SQLException ex) {
            System.out.println("Oshibka deleteAuthor(id) execute" + ex.getMessage());
        }

        query = "DELETE FROM Authors WHERE author_id = " + id + ";";

        try {
            b = st.execute(query);
            int k = 0;
            k = st.getUpdateCount();
            System.out.println("Vsego zamen deleteAuthor id" + k);
        } catch (SQLException ex) {
            System.out.println("Oshibka deleteAuthor(Author author) execute" + ex.getMessage());
        }
        return b;
    }


@Override
        public void close() {
        stopServer();
    }

    private List<Object> printResult(ResultSet rs) {// s realizaciey perebora vbIborki
        if (rs != null) {
            try {
                ResultSetMetaData md = rs.getMetaData();//yznaem kakaya y nas tablica
                int cols = md.getColumnCount();//yznaem skolbko kolonok
                for (int i = 1; i <= cols; ++i) {
                    System.out.print(md.getColumnName(i) + "\t\t");// poluchim strokovoe nazvanie kolonki
                }
                System.out.println("\n---------------------------");

                while (rs.next()) {//poka estb sledyushaya zapisb

                    for (int i = 1; i <= cols; ++i) {
                        switch (md.getColumnType(cols)) {
                            case Types.INTEGER://esli tip bydet int to v sout mbI ispolzuem korrektnyi metod izvlecheniya znacheniya getInt
                                System.out.print(rs.getString(i) + "\t\t");//Anomaliya rs.getInt(i)!!!
                                list.add(rs.getString(i));
                                break;

                            case Types.VARCHAR:
                                System.out.print(rs.getString(i) + "\t\t");
                                list.add(rs.getString(i));
                                break;
                            case Types.CHAR:
                                System.out.print(rs.getString(i) + "\t\t");
                                list.add(rs.getString(i));
                                break;
                            case Types.DATE:
                                System.out.print(rs.getDate(i) + "\t\t");
                                list.add(rs.getDate(i).toString());
                                break;
                        }//switch end  
                    }//FOR end
                    System.out.println();
                }
            } catch (SQLException ex) {
                System.out.println("Error v printResult(ResultSet rs) " + ex.getMessage());
            }
        }
        return list;
    }//printResult() end

}//class end
