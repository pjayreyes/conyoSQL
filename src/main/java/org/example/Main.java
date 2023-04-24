package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private final static String STRING_CONN = "jdbc:sqlite:sqlite-tools-win32-x86-3410200/BGC";
    private static Connection conn;

    private static final Map<String, String> KEYWORD_MAPPING = new HashMap<>();

    static {
        // Define keyword mapping
        KEYWORD_MAPPING.put("DUDE", "");
        KEYWORD_MAPPING.put("PARE", "");
        KEYWORD_MAPPING.put("CHONG", "");
        KEYWORD_MAPPING.put("TARA", "");
        KEYWORD_MAPPING.put("TAYO", "");
        KEYWORD_MAPPING.put("MO", "");
        KEYWORD_MAPPING.put("NA", "");
        KEYWORD_MAPPING.put("YAN", "");
        KEYWORD_MAPPING.put("AT", "");

        KEYWORD_MAPPING.put("GET", "SELECT");
        KEYWORD_MAPPING.put("SA", "FROM");
        KEYWORD_MAPPING.put("BRING", "INSERT");
        KEYWORD_MAPPING.put("THERE", "INTO");
        KEYWORD_MAPPING.put("WITH", "VALUES");
        KEYWORD_MAPPING.put("EKIS", "DELETE");
        KEYWORD_MAPPING.put("SI", "WHERE chikababes = ");

        // TODO: implement UPDATE
    }

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        System.out.print("> ");
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();

        while (!line.contains("exit")) {
            System.out.print("> ");

            if(!line.isEmpty()) {
                // interpret the line
                String[] parts = line.split(" ");
                StringBuilder interpretedQuery = new StringBuilder();

                // map keywords
                for(int i = 0; i<parts.length; i++) {
                    String keyword = parts[i].toUpperCase();

                    if(KEYWORD_MAPPING.containsKey(keyword)) {
                        keyword = KEYWORD_MAPPING.get(keyword);
                    }

                    if(!keyword.isEmpty()) interpretedQuery.append(keyword).append(" ");
                }

                executeQuery(String.valueOf(interpretedQuery));

                System.out.println();
            }

            line = br.readLine();
        }
    }

    private static void executeQuery(String query) throws ClassNotFoundException, SQLException {
        // establish db connection
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection(STRING_CONN);

        Statement stmt = conn.createStatement();
        String[] firstWord = query.split(" ");
        switch (firstWord[0]) {
            case "SELECT":
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()) {
                    System.out.println(rs.getString("chikababes"));
                }
                break;

            case "INSERT":
                stmt.execute(query);
                break;

            case "DELETE":
                stmt.execute(query);
                break;
        }

        conn.close();
    }
}