/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.tpi135;

import java.sql.*;

/**
 *
 * @author aragon598
 */
public class Employee {
    
    private String employeeUid;
    private String firstName;
    private String lastName;
    private String ssn;
    private String startDate;
    
    public Employee(String number, String stDate, String Name, String securityNumber,String lastName) throws Exception {
        if ((number == null) && (lastName == null)) {
            throw new IllegalArgumentException("El número del empleado y el apellido no pueden ser nulos");
        }
        
        this.employeeUid = number;
        this.startDate = stDate;
        this.firstName = Name;
        this.ssn = securityNumber;
        this.lastName = lastName;
    }
    
    public String getEmployeeUid() {
        return this.employeeUid;
    }
    
    public String getStartDate() {
        return this.startDate;
    }
    
    public String getFirstName() {
        return this.firstName;
    }
    
    public String getSsn() {
        return this.ssn;
    }
    
    public String getLastName() {
        return this.lastName;
    }
    
    public void InsertEmployee() throws Exception {
        // El método InsertEmployee() inserta el Empleado en la tabla employees de la BD HR
        // Para esto se establece la conexión con la BD en MySQL, y se manda a ejecutar la instrucción
        // INSERT correspondiente en la BD
        // la variable con es del tipo java.sql.Connection y a través de ella se maneja la conexión con la BD

        Connection con = null;
        Statement stmt = null;
        String strSentence;
        //
        // En la variable strSentence se almacena la instrucción SQL INSERT que se va a mandar a ejecutar
        //
        strSentence = "INSERT INTO employees (employeeUid, startDate, firstName, ssn, lastName) VALUES (\"" +
                                                                                                                                this.getEmployeeUid() + "\",\"" + this.getStartDate() + "\",\""+
                                                                                                                                this.getFirstName() + "\",\""+ 
                                                                                                                                this.getSsn() + "\",\"" + 
                                                                                                                                this.getLastName() + "\")";
        try {
            // En este bloque try se establece una conexión con la BD
            // En el String url se define la variable url de JDBC, que es la dirección para conectarse a la BD HR
            //
            String url = "jdbc:mariadb:///HR";
            // En la siguiente insrucción se define el contrlador que debe cargarse para manejar la conexión
            // con la BD, que enste caso es el org.gjt.mm.mysql.Driver
            //
            Class.forName("org.mariadb.jdbc.Driver");
            // Class.forName("org.gjt.mm.mysql.Driver");
            // En la siguiente línea se intenta realizar una conexión con la BD, asignándola a la variable con
            // y en esta se especifica la variable URL, y el login y la password para conectarse a la BD
            //
            con = DriverManager.getConnection(url, "root", "password");
            // Una vez establecida la conexióncon la BD, se pueden ejecutar instrucciones SQL en ella usando
            // la conexión
            // Para ejecutar una instrucción SQL en la BD primero se crea una variable que se usará para ejecutar
            // la instrucción, que en nuestro caso va a ser la variable stmt
            // Se ejecuta la instrucción con.createStatement() que crea y abre un canal de comunicación medante
            // el que se pueden ejecutar las consultas
            //
            stmt = con.createStatement();
            // Una vez abierto el canal de comunicación entre la aplicación y la BD se manda a ejecutar la
            // instrucción SQL, que en nuestro caso está almacenada en el StringstrSentence
            // usando la instrucción stmt.executeUpdate(strSentence)
            //
            stmt.executeUpdate(strSentence);
            System.out.println("Los valores se añadieron a la BD");
        }catch (SQLException e) {
            e.printStackTrace();
            System.out.println("La regaste no era así jajaja ");
        }finally{
            if (con != null) {
                try {
                con.close();
                // Una vez ejecutada la consulta se cierra la conexión

                //abierta con la instrucción stmt.close()
                //

                stmt.close();
                } catch (Exception e) {
                System.out.println(e.getMessage());
                }
            }

        }
    }
}
