package org.example;

import java.io.*;

public class UserOperations {

    private final String file = "users.hot";

    void addUser(String username, String password) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(username + " | " + password);
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    boolean  readUser(String username, String password){
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;

            while ((line = br.readLine()) != null) {

                String[] parts = line.split(" \\| ");

                if (parts.length == 2) {
                    String user = parts[0].trim();
                    String pass = parts[1].trim();

                    if (user.equals(username)) {
                        if (pass.equals(password)) {
                            br.close();
                            return true;
                        } else {
                            br.close();
                            return false;
                        }
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
