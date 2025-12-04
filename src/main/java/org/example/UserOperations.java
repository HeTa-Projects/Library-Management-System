package org.example;

import java.io.*;

public class UserOperations {

    private final String file = "users.hot";
    private final String admin_file = "admins.hot";

    void addUser(String username, String password, String email) {
        try (FileWriter fw = new FileWriter(file, true);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write(username + " | " + password + " | " + email);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean readUser(String username, String password) {
        try (FileReader fr = new FileReader(file);
             BufferedReader br = new BufferedReader(fr)) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s*\\|\\s*");

                if (parts.length >= 2) {
                    String user = parts[0].trim();
                    String pass = parts[1].trim();

                    if (user.equals(username)) {
                        return pass.equals(password);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserEmail(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length >= 3) {
                    String userFromFile = parts[0].trim();
                    if (userFromFile.equals(username)) {
                        return parts[2].trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
    public boolean readAdmin(String username, String password) {

        try (BufferedReader br = new BufferedReader(new FileReader(admin_file))) {

            String line;

            while ((line = br.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split("\\|");

                if (parts.length < 2) continue;

                String fileUser = parts[0].trim();
                String filePass = parts[1].trim();

                if (fileUser.equals(username) && filePass.equals(password)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}