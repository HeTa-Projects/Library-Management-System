package org.example;

import java.io.*;

public class UserOperations {

    private final String file = "users.hot";
    private final String admin_file = "admins.hot";

    void addUser(String username, String password, String email, String tc) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

            bw.write(username + " | " + password + " | " + email + " | " + tc);
            bw.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    boolean readUser(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 4) continue;

                String user = parts[0];
                String pass = parts[1];

                if (user.equals(username) && pass.equals(password)) {
                    return true;
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
                if (parts.length < 4) continue;

                if (parts[0].equals(username)) {
                    return parts[2];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getUserTc(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 4) continue;

                if (parts[0].equals(username)) {
                    return parts[3];
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isTcUsed(String tc) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\s*\\|\\s*");
                if (parts.length < 4) continue;

                if (parts[3].equals(tc)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean readAdmin(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(admin_file))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] parts = line.split("\\|");
                if (parts.length < 2) continue;

                if (parts[0].trim().equals(username)
                        && parts[1].trim().equals(password)) {
                    return true;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}