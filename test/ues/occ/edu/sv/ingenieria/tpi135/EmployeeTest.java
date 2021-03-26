/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ues.occ.edu.sv.ingenieria.tpi135;

//~--- non-JDK imports --------------------------------------------------------
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.IDatabaseTester;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;

import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.junit.internal.runners.TestClassRunner;
import org.junit.runner.RunWith;
//~--- JDK imports ------------------------------------------------------------
import java.io.File;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author christian
 */
@RunWith(TestClassRunner.class)
public class EmployeeTest extends DBTestCase {

    public EmployeeTest() {
//super();
/*
* Método constructor de la clase DBEmployeeTest
* donde se usa el método setProperty de la clase System
* para que la clase System conozca los datos de la conexión con la BD
* como son:
* - EL driver de acceso a la BD, que es "org.gjt.mm.mysql.Driver"
* (también se puede colocar como driver el valor "com.mysql.jdbc.Driver" si
se usó
* el driver jdbc que ya trea instalado Netbeans en ss librerias)
* - URL de la conexión con la BD HR, que es jdbc:mysql:///HR
* - Login y password para acceder la BD
         */
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,"com.mysql.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,"jdbc:mysql:///HR");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,"hola123");
    }

    @Override
    protected DatabaseOperation getSetUpOperation() throws Exception {
        return DatabaseOperation.CLEAN_INSERT;
    }

    @Override
    protected void setUp() throws Exception {
// super.setUp();
        final IDatabaseTester databaseTester = getDatabaseTester();
        assertNotNull("DatabaseTester is not set", databaseTester);
        databaseTester.setSetUpOperation(getSetUpOperation());
        databaseTester.setDataSet(getDataSet());
        databaseTester.onSetup();
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return null; // new FileInputStream("C:/employee-hr-seed-1-registro.xml"));
    }

    @Test
    public void testInsertEmployee() throws Exception {
//
// Inicialización del caso de prueba
// Se crea un Objeto de la clase Employee, que será almacenado en la BD con elmétodo a probar 
//
        String idEmp = "8";
        String startDate = "2005-04-04";
        String firstname = "Peter";
        String ssn = "000-90-0001";
        String lastname = "Gabriel";
        Employee instance = new Employee(idEmp, startDate, firstname, ssn, lastname);
        try {
// (1) Inicialización del contenido de la Base de Datos
// Se inicializa la base de datos

// En este caso al usar la instrucción TRUNCATE_TABLE de la clase de DBUnit
// DatabaseOperation, se borran todas las filas existentes en la BD,dejándola vacía.
// La clase DatabaseOperation permite realizar operaciones sobre la BD
// para definir el contenido de la BD antes y después de ejecutar
// los métodos de prueba
//
IDatabaseConnection dbConnection = this.getConnection();
            IDataSet dsInicialization = new FlatXmlDataSet(new File("/home/christian/NetBeansProjects/PruebaDbUnit"));
            DatabaseOperation.TRUNCATE_TABLE.execute(dbConnection, dsInicialization);
// (2) Ejecución del método a probar
            instance.InsertEmployee();
// (3) Obtención del contenido de la tabla Empleado como está en la BD.
// Para esto se usa el método getDataSet de la Interfaz IDatabaseConnection
// que accede a la BD y retorna un dataset que refleja su contenido
// que se asigna a dsObtained
//
         IDataSet dsObtained = getConnection().createDataSet();
// (4) Obtención del contenido esperado
// Para esto se construye un dataset en base al contenido experado de la BD
// después de ejecutar la prueba y que se encuentra definido como
// un documento xml que se encuentra almacenado en el archivo
// C:/employee-hr-seed-1-registro.xml, su estrcutura es la siguiente:
// <?xml version='1.0' encoding='UTF-8'?>
// <dataset>
// <employees employeeUid='8'
// startDate='2005-04-04'
// firstName='Peter' ssn='000-90-0001'
// lastName='Gabriel' />
// </dataset>
//
// El dataset construido se almacena en dsExpected
//
            IDataSet dsExpected = new FlatXmlDataSet(new File("C:/employee-hr-seed-1-registro.xml"));
// (5) Verificación
// Se verifica que los contenidos de los datasets esperados y obtenidos
// son iguales, que es lo que se espera
// usando los métodos Asserts que provee DBUnit
//
            Assertion.assertEquals(dsObtained, dsExpected);
//} catch (SQLException e) {
// e.printStackTrace();
// fail("Error al ejecutar un caso de prueba");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error al ejecutar un caso de prueba");

        }
    }
}
