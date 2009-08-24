package gob.pe.sunarp.extranet.dbobj;
import java.net.*;
import java.util.*;
import com.jcorporate.expresso.core.utility.*;
import com.jcorporate.expresso.core.ExpressoSchema;
import com.jcorporate.expresso.core.db.*;
import com.jcorporate.expresso.core.dbobj.SecuredDBObject;
import com.jcorporate.expresso.core.dbobj.Schema;
import com.jcorporate.expresso.core.dbobj.DBObject;
import com.jcorporate.expresso.services.dbobj.*;
import com.jcorporate.expresso.core.security.User;
import com.jcorporate.expresso.core.misc.DateTime;
import com.jcorporate.expresso.core.misc.StringUtil;
import com.jcorporate.expresso.services.html.HtmlException;
import com.jcorporate.expresso.core.misc.ConfigManager;
import com.jcorporate.expresso.core.misc.ConfigurationException;
import com.jcorporate.expresso.core.ExpressoSchema;
import com.jcorporate.expresso.core.controller.Controller;
import com.jcorporate.expresso.core.job.ServerException;
import com.jcorporate.expresso.core.job.Job;
import com.jcorporate.expresso.core.misc.Base64;
import java.util.Calendar;
import java.io.*;
import java.sql.*;
import com.jcorporate.expresso.services.html.*;
import com.jcorporate.expresso.core.servlet.StdServlet;
import com.jcorporate.expresso.core.i18n.Messages;
import org.apache.log4j.Category;
import com.jcorporate.expresso.core.logging.LogManager;
import gob.pe.sunarp.extranet.framework.Loggy;

public class DBToolGenerator extends Thread {

	private static String[] tablasOracle = {
		"ABONO",
		"ACTOS_TITULO",
		"ASIENTO",
		"AUD_AFILIACION",
		"AUD_BUSQ_MULT_SEDES",
		"AUD_BUSQ_PARTIDA",
		"AUD_BUSQ_RAZ_SOC_PJ",
		"AUD_BUSQ_REG_AERO",
		"AUD_BUSQ_REG_EMB",
		"AUD_BUSQ_REG_MIN",
		"AUD_BUSQ_REG_PREDIOS",
		"AUD_BUSQ_TITULO",
		"AUD_CONSULTA_PARTIDA",
		"AUD_CONSULTA_TITULO",
		"AUD_VISUALIZ_PARTIDA",
		//"CI_ENTIDAD",
		//"CI_IMAGENES",
		"COMANDOS",
		"COMPROBANTE",
		"CONSUMO",
		"CONTRATO",
		"CUENTA",
		"CUENTA_JURIS",
		"DERE_MINE",
		"DETALLE_TITULO",		
		"DIRECCION",
		"ESQUELA",
		"EXTORNO",
		"EX_RECEPCION",
		"FICHA",
		"IND_PJ_PARTIC_TITU",
		"IND_PN_PARTIC_TITU",
		"IND_PRTC",
		"LINEA_PREPAGO",
		"MAIL",
		"MEDIOS_PAGO",
		"MOVIMIENTO",
		"OFIC_REGISTRAL",
		"ORG_CTAS",
		"PAGO_CHEQUE",
		"PAGO_EN_LINEA",
		"PARAMETROS",
		"PARTIC_LIBRO",
		"PARTIDA",
		"PE_JURI",
		"PE_NATU",
		"PERFIL_CUENTA",
		"PERMISO_PERFIL",
		"PERMISO_USR",
		"PERSONA",
		"PRESENTANTE",
		"PRTC_JUR",
		"PRTC_NAT",
		"RAZ_SOC_PJ",
		"REG_AERONAVES",
		"REG_BUQUES",
		"REG_EMB_PESQ",
		"REG_MINERIA",
		"REG_PREDIOS",
		"REGIS_PUBLICO",
		"RESERVA_RZ_SOC",
		"RUBRO_LIBRO",
		"SOCI_MINE",
		"TA_BLOQ_PARTIDA",
		"TARIFA",
		"TIPO_INTERIOR",
		"TIPO_NUMERACION",
		"TIPO_VIA",
		"TIPO_ZONA",
		"TITU_SUSPEN",
		"TITULO",
		"TM_ACTO",
		"TM_AREA_REGISTRAL",
		"TM_BANCO",
		"TM_DEPARTAMENTO",
		"TM_DISTRITO",
		"TM_DOC_IDEN",
		"TM_ERROR",
		"TM_ESTADO_TITULO",
		"TM_GIRO",
		"TM_INST_SIR",
		"TM_JURISDICCION",
		"TM_LIBRO",
		"TM_NIVEL_ACCESO",
		"TM_PAIS",
		"TM_PERFIL",
		"TM_PERFIL_ASIGNAC",
		"TM_PERMISO_ASIG",
		"TM_PERMISO_EXT",
		"TM_PREG_SECRETAS",
		"TM_PROVINCIA",
		"TM_RUBRO",
		"TM_SERVICIO",
		"TM_TIPO_ESQUELA",
		"TM_TIPO_PARTIC",
		"TOMO_FOLIO",
		"TRANSACCION",
		"USO_SERVICIO",
		"VER_CONTRATO",
		"VW_BUSQ_RAZ_SOC",
		"VW_MOVIMIENTO",
		"VW_DIARIORECAUDA",
		"VW_USO_SERVICIO"
};

	private static String[] autoInc = {
		"ABONO",
		"AUD_AFILIACION",
		"AUD_BUSQ_PARTIDA",
		"AUD_BUSQ_RAZ_SOC_PJ",
		"AUD_BUSQ_REG_AERO",
		"AUD_BUSQ_REG_EMB",
		"AUD_BUSQ_REG_MIN",
		"AUD_BUSQ_REG_PREDIOS",
		"AUD_BUSQ_TITULO",
		"AUD_CONSULTA_PARTIDA",
		"AUD_CONSULTA_TITULO",
		"AUD_VISUALIZ_PARTIDA",
		"COMPROBANTE",
		"CONSUMO",
		"CONTRATO",
		"CUENTA",
		"EXTORNO",
		"LINEA_PREPAGO",
		"MAIL",
		"MEDIOS_PAGO",
		"MOVIMIENTO",
		"PAGO_EN_LINEA",
		"PARAMETROS",
		"PARTIDA",
		"PE_JURI",
		"PE_NATU",
		"PERSONA",
		"TARIFA",
		"TITULO",
		"TM_BANCO",
		"TM_ESTADO_TITULO",
		"TM_GIRO",
		"TM_JURISDICCION",
		"TM_NIVEL_ACCESO",
		"TM_PERFIL",
		"TM_PREG_SECRETAS",
		"TM_SERVICIO",
		"TRANSACCION",
		"VER_CONTRATO"
	};

	private static Hashtable oracleT = new Hashtable();
	
    private static String thisClass = new String(
        "com.jcorporate.expresso.core.utility.DBTool.");
    private static String dbName = null;

    private static Category log = null;

	private static String paquet = null;

    private static void setupLog() {
        LogManager.instantiate();
        log = Category.getInstance("expresso.core.utility.DBTool");
    }

    /**
     * Constructor
     *
     * @throws    DBException If the database cannot be contacted
     * @throws    ServerException If another uncaught exception occurs
     */
    public DBToolGenerator() throws DBException, ServerException {
        String myName = new String(thisClass + "TimerServer()");
    } /* DBTool() */


    /**
     * Choose a schema to enter Setup values for
     *
     * @param   dbName
     * @throws  DBException
     * @throws  IOException
     */
    private static void chooseConfig(String dbName)
        throws DBException, IOException {

        setupLog();

        //if (Loggy.isTrace(this)) System.out.println("Choose Schema: (Database " + dbName + ")");
        Hashtable choices = new Hashtable();
        choices.put("1",
            "Expresso|com.jcorporate.expresso.core.ExpressoSchema");
        int nextNum = 1;
        SchemaList sl = new SchemaList();
        sl.setDBName(dbName);
        SchemaList oneSchemaList = null;
        String nextKey = null;
        for (Enumeration e = sl.searchAndRetrieve().elements();
            e.hasMoreElements();) {

            nextNum++;
            oneSchemaList = (SchemaList) e.nextElement();
            nextKey = "" + nextNum;
            choices.put(nextKey, oneSchemaList.getField("Descrip") + "|"
                + oneSchemaList.getField("SchemaClass"));
        }

        /* Now present the menu */

        String oneNumber = null;
        String oneDescrip = null;
        String oneClassName = null;
        for (Enumeration ex = choices.keys(); ex.hasMoreElements();) {
            oneNumber = (String) ex.nextElement();
            StringTokenizer stk = new StringTokenizer((String) choices.get(
                oneNumber), "|");
            oneDescrip = stk.nextToken();
            //if (Loggy.isTrace(this)) System.out.println(oneNumber + ". " + oneDescrip);
        }
        //if (Loggy.isTrace(this)) System.out.println("");
        //if (Loggy.isTrace(this)) System.out.print("Enter the number corresponding to the schema that "
        //    + "you wish to enter/edit Setup values for:");
        BufferedReader br = new BufferedReader(new InputStreamReader(
            System.in));
        String command = StringUtil.notNull(br.readLine());
        if (command.equals("")) {
            return;
        } else {
            StringTokenizer stk2 = new StringTokenizer((String) choices.get(
                command), "|");
            stk2.nextToken();
            enterConfig(dbName, stk2.nextToken());
        }
    } /* chooseConfig(String) */


    /**
     *
     *
     * @throws  DBException
     * @throws  SQLException
     */
    private static void doBenchMarks() throws DBException, SQLException, ConfigurationException {
        setupLog();

        //if (Loggy.isTrace(this)) System.out.println("Benchmarks begin at "
        //    + DateTime.getDateTimeString());
        int retryCount = 5000;

        //if (Loggy.isTrace(this)) System.out.println("This benchmark tests the performance of the "
        //    + "connection pool in simple \n"
        //    + " allocations of connections - it makes & releases " + retryCount
        //    + " connections, 5 at a time.\n");
        //if (Loggy.isTrace(this)) System.out.println("Begin connection pool tests:"
        //    + DateTime.getDateTimeString());
        DBConnectionPool myPool = DBConnectionPool.getInstance();

        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End connection pool tests:"
        //    + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println("Compare this to making & breaking the connection "
        //    + "to the DB the same number\n"
        //    + " of times.");
        String dbURL = ConfigManager.getJdbcRequired("default").getUrl();
        String dbLogin = ConfigManager.getJdbcRequired("default").getLogin();
        String dbPassword = ConfigManager.getJdbcRequired("default").getPassword();

        //if (Loggy.isTrace(this)) System.out.println("Begin direct connection tests:"
        //    + DateTime.getDateTimeString());
        for (int i = 0; i <= retryCount; i++) {
            Connection conn1 = DriverManager.getConnection(dbURL, dbLogin,
                dbPassword);
            Statement stmnt = conn1.createStatement();
            conn1.close();

            Connection conn2 = DriverManager.getConnection(dbURL, dbLogin,
                dbPassword);
            Statement stmnt2 = conn2.createStatement();
            conn2.close();

            Connection conn3 = DriverManager.getConnection(dbURL, dbLogin,
                dbPassword);
            Statement stmnt3 = conn3.createStatement();
            conn3.close();

            Connection conn4 = DriverManager.getConnection(dbURL, dbLogin,
                dbPassword);
            Statement stmnt4 = conn4.createStatement();
            conn4.close();

            Connection conn5 = DriverManager.getConnection(dbURL, dbLogin,
                dbPassword);
            Statement stmnt5 = conn5.createStatement();
            conn5.close();
        }
        //if (Loggy.isTrace(this)) System.out.println("End direct connection tests:"
        //    + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println("Direct SQL tests - the above test is repeated, "
        //    + "but with a series of simple \n"
        //    + " SQL queries executed in each of the connections.");
        //if (Loggy.isTrace(this)) System.out.println("Begin direct SQL tests:"
        //    + DateTime.getDateTimeString());
        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            conn1.execute("SELECT * FROM USERLOGIN WHERE UserName = 'Admin'");

            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            conn2.execute(
                "SELECT * FROM GROUPMEMBERS WHERE UserName = 'Admin'");

            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            conn3.execute(
                "SELECT * FROM SETUP WHERE SetupCode = 'ServletEvent'");

            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            conn4.execute(
                "SELECT * FROM DBOBJSECURITY WHERE GroupName = 'Admin'");

            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            conn5.execute("SELECT * FROM EVENT WHERE Event = 'SYSERROR'");

            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End direct SQL tests:"
        //    + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println("DB Object tests - the above test is repeated, "
        //    + "but with db objects \n"
        //    + " doing the queries rather than direct sql");
        //if (Loggy.isTrace(this)) System.out.println("Begin db object tests:"
        //    + DateTime.getDateTimeString());
        GroupMembers gm = new GroupMembers();
        UserDBObj oneUser = new UserDBObj();
        Setup s1 = new Setup();
        DBObjSecurity dbo = new DBObjSecurity();
        Event e = new Event();

        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            oneUser.setConnection(conn1);
            oneUser.setField("UserName", "Admin");
            oneUser.find();

            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            gm.setConnection(conn2);
            gm.setField("UserName", "Admin");
            gm.find();

            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            s1.setConnection(conn3);
            s1.setField("SetupCode", "ServletEvent");
            s1.find();

            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            dbo.setConnection(conn4);
            dbo.setField("GroupName", "Admin");
            dbo.find();

            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            e.setConnection(conn5);
            e.setField("Event", "SYSERROR");
            e.find();

            //if (Loggy.isTrace(this)) System.out.println("Benchmarks begin at " + DateTime.getDateTimeString());


            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }

        retryCount = 5000;
        //if (Loggy.isTrace(this)) System.out.println("End dbobject tests:"
        //    + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println(
        //    "This benchmark tests the performance of the "
        //        + "connection pool in simple \n"
        //        + " allocations of connections - it makes & releases "
        //        + retryCount
        //        + " connections, 5 at a time.\n");
        //if (Loggy.isTrace(this)) System.out.println(
        //    "Begin connection pool tests:" + DateTime.getDateTimeString());

        myPool = DBConnectionPool.getInstance();


        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End connection pool tests:" + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println(
        //    "Compare this to making & breaking the connection "
        //        + "to the DB the same number\n"
        //        + " of times.");

        dbURL = ConfigManager.getJdbcRequired("default").getUrl();
        dbLogin = ConfigManager.getJdbcRequired("default").getLogin();
        dbPassword = ConfigManager.getJdbcRequired("default").getPassword();


        //if (Loggy.isTrace(this)) System.out.println(
        //    "Begin direct connection tests:" + DateTime.getDateTimeString());
        for (int i = 0; i <= retryCount; i++) {
            Connection conn1 = DriverManager.getConnection(dbURL, dbLogin, dbPassword);
            Statement stmnt = conn1.createStatement();
            conn1.close();

            Connection conn2 = DriverManager.getConnection(dbURL, dbLogin, dbPassword);
            Statement stmnt2 = conn2.createStatement();
            conn2.close();

            Connection conn3 = DriverManager.getConnection(dbURL, dbLogin, dbPassword);
            Statement stmnt3 = conn3.createStatement();
            conn3.close();

            Connection conn4 = DriverManager.getConnection(dbURL, dbLogin, dbPassword);
            Statement stmnt4 = conn4.createStatement();
            conn4.close();

            Connection conn5 = DriverManager.getConnection(dbURL, dbLogin, dbPassword);
            Statement stmnt5 = conn5.createStatement();
            conn5.close();
        }
        //if (Loggy.isTrace(this)) System.out.println(
        //    "End direct connection tests:" + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println(
        //    "Direct SQL tests - the above test is repeated, "
        //        + "but with a series of simple \n"
        //        + " SQL queries executed in each of the connections.");
        //if (Loggy.isTrace(this)) System.out.println("Begin direct SQL tests:" + DateTime.getDateTimeString());
        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            conn1.execute("SELECT * FROM USERLOGIN WHERE UserName = 'Admin'");

            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            conn2.execute("SELECT * FROM GROUPMEMBERS WHERE UserName = 'Admin'");

            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            conn3.execute("SELECT * FROM SETUP WHERE SetupCode = 'ServletEvent'");

            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            conn4.execute("SELECT * FROM DBOBJSECURITY WHERE GroupName = 'Admin'");

            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            conn5.execute("SELECT * FROM EVENT WHERE Event = 'SYSERROR'");

            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End direct SQL tests:" + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println(
        //    "DB Object tests - the above test is repeated, "
        //        + "but with db objects \n"
        //        + " doing the queries rather than direct sql");
        //if (Loggy.isTrace(this)) System.out.println("Begin db object tests:" + DateTime.getDateTimeString());

        gm = new GroupMembers();
        oneUser = new UserDBObj();
        s1 = new Setup();
        dbo = new DBObjSecurity();
        e = new Event();


        for (int i = 0; i <= retryCount; i++) {
            DBConnection conn1 = myPool.getConnection("Test " + (i + 1));
            // Modified 05/20/01 by Shash Chatterjee
            // Used to use UserDBObj directly....but Uid as key complicates things....
            // oneUser.setConnection(conn1);
            oneUser.setField("UserName", "Admin");
            oneUser.find();

            DBConnection conn2 = myPool.getConnection("Test " + (i + 2));
            gm.setConnection(conn2);
            gm.setField("UserName", "Admin");
            gm.find();

            DBConnection conn3 = myPool.getConnection("Test " + (i + 4));
            s1.setConnection(conn3);
            s1.setField("SetupCode", "ServletEvent");
            s1.find();

            DBConnection conn4 = myPool.getConnection("Test " + (i + 5));
            dbo.setConnection(conn4);
            dbo.setField("GroupName", "Admin");
            dbo.find();

            DBConnection conn5 = myPool.getConnection("Test " + (i + 6));
            e.setConnection(conn5);
            e.setField("Event", "SYSERROR");
            e.find();

            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End dbobject tests:" + DateTime.getDateTimeString());

        //if (Loggy.isTrace(this)) System.out.println("Benchmarks end at "
        //    + DateTime.getDateTimeString());

    } /* doBenchMarks() */

    /**
     * Delete Table - Deletes a table from the specified db
     *
     * @dbName - the name of the db that contains this table
     * @tableName - The name of the table to delete.
     */
     public static void deleteTable(String dbName, String tableName)
                                            throws DBException {

        DBConnectionPool pool = DBConnectionPool.getInstance(dbName);


        String stmnt = "DROP TABLE " + tableName + ";";
        pool.executeExclusiveUpdate(stmnt);
    }

    /**
     * Method wipes out more than one installed schema.
     *
     */
    public static synchronized void deleteSchema(Page myPage, String dbName,
                    Vector schemas)
                    throws DBException, HtmlException, IllegalArgumentException {
        setupLog();

        log.info("Removing " + Integer.toString(schemas.size()) + " schemas");

        for (Enumeration e = schemas.elements(); e.hasMoreElements();) {
            String oneName = ((Schema)e.nextElement()).getClass().getName();
            deleteSchema(myPage,dbName,oneName);
        }

        log.info("Schema Deletion Complete");

    }

	//cjvc
	private static void carga_data(){
		for(int i = 0; i < tablasOracle.length; i++){
			oracleT.put(tablasOracle[i], tablasOracle[i]);
		}
	}
	
	private static boolean pasaTabla(String tabla){
		return oracleT.containsKey(tabla);
	}
	//cjvc
    /**
     * Method to remove an installed schema.  Deletes all the tables associated
     * with the schema
     *
     * @param    myPage - The page to output the 'log' to.
     * @param    dbName - the name of the database to perform this operation
     * @param   schemaName - the full classname of the schema to delete.
     * @throws  IllegalArgumentException if the schema doesn't exist.
     * @throws    DBException If there is a database problem creating
     *            the new default values
     * @throws    HtmlException If there is a problem building the
     *            confirmation page
     */
    public static synchronized void deleteSchema(Page myPage, String dbName,
                    String schemaName)
                    throws DBException, HtmlException, IllegalArgumentException {

        final String myName = new String(thisClass + "deleteSchema(Page,String,String)");


        setupLog();


        boolean expressoDelete = false;

        log.info(myName + ": removing schema: " + schemaName + " from db " + dbName);

        if (schemaName.equals("com.jcorporate.expresso.core.ExpressoSchema")) {
            expressoDelete = true;
        }


        Schema oneSchema=null;

        try {
            oneSchema = (Schema)Class.forName(schemaName).newInstance();
        } catch(Throwable e) {
           throw new IllegalArgumentException("Unable to load class " + schemaName
                + " " + e.getMessage());
        }


        DBObject oneObject;

        try {
            //Make sure all connections are dropped first - otherwise tables
            //will be locked
            DBConnectionPool.reInitialize();
        } catch (DBException e) {}


        for (Enumeration e = oneSchema.getMembers(); e.hasMoreElements(); ) {
            oneObject = (DBObject) e.nextElement();

            if (myPage != null) {
                myPage.add(new Paragraph(new Text("Removing Table" + " "
                    + oneObject.getTargetTable()) )  );
            }

            try {
                deleteTable(dbName,oneObject.getTargetTable());
            } catch (DBException dbe) {
                log.warn("Unable To Delete Table: " + oneObject.getTargetTable() +
                        " DB Message: " + dbe.getDBMessage());
            }
        }

        //If schema is not expresso, we also have to clear the
        //setup table
        if (expressoDelete == false) {
            try {
                //First remove the schema entries
                SchemaList schemaEntry = new SchemaList(SecuredDBObject.SYSTEM_ACCOUNT);
                schemaEntry.setDBName(dbName);
                schemaEntry.setField("SchemaClass",schemaName);
                schemaEntry.delete();
            } catch (DBException e) {
                log.info("Unable to delete schema entry.",e);
            }

            //Now remove the setup values
            Setup setupList = new Setup(SecuredDBObject.SYSTEM_ACCOUNT);
            setupList.setDBName(dbName);
            setupList.setField("SchemaClass", schemaName);

            Enumeration es = setupList.searchAndRetrieve().elements();
            Setup s = null;
            while(es.hasMoreElements()) {
                try {
                    s = (Setup)es.nextElement();
                    s.setDBName(dbName);
                    s.delete();
                } catch (DBException e) {
                    if (s != null) {
                        log.info("Unable to delete setup value: " +
                                s.getField("SetupCode"), e);
                    } else {
                        log.info("Unable to delete setup value: (s=null)", e);
                    }
                }
            }
        }

        if (myPage != null) {
            myPage.add(new Paragraph(new Text("All Tables Removed")));
        }
    } /* otherSetups(PrintWriter, Vector, String) */


    /**
     *
     *
     * @param   dbName
     * @param   schemaClass
     * @throws  DBException
     * @throws  IOException
     */
    private static void enterConfig(String dbName, String schemaClass)
        throws DBException, IOException {

        setupLog();

        //if (Loggy.isTrace(this)) System.out.println("Setup/Configuration values: (Database "
        //    + dbName + ", Schema " + schemaClass + ")");

        Setup setupList = new Setup();
        setupList.setDBName(dbName);

        String command = new String("");
        setupList.setField("SchemaClass", schemaClass);

        Enumeration es = setupList.searchAndRetrieve().elements();
        if (!es.hasMoreElements()) {
            //if (Loggy.isTrace(this)) System.out.println(
            //    "There were no default setup values in database/context '"
            //    + dbName
            //    + "'. You must run initial setup to create the defaults "
            //    + "first.");
        }

        Setup oneSetup = null;
        while (es.hasMoreElements()) {
            oneSetup = (Setup) es.nextElement();

            int i = 0;
            //if (Loggy.isTrace(this)) System.out.println("---------------------------------");
            //if (Loggy.isTrace(this)) System.out.println("Setup Code   :" + oneSetup.getField(
            //    "SetupCode"));
            //if (Loggy.isTrace(this)) System.out.println("Description  :" + oneSetup.getField("Descrip"));
            //if (Loggy.isTrace(this)) System.out.println("Current Value:" + oneSetup.getField(
            //    "SetupValue"));
            //if (Loggy.isTrace(this)) System.out.println("");
            //if (Loggy.isTrace(this)) System.out.print(
            //    "Press enter to leave current value, or enter new value:");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                System.in));
            command = StringUtil.notNull(br.readLine());
            if (!command.equals("")) {
                //if (Loggy.isTrace(this)) System.out.println("Value for code '"
                //    + oneSetup.getField("SetupCode") + "' "
                //    + " now '" + command + "'");
                oneSetup.setField("SetupValue", command);
                oneSetup.update();
                //if (Loggy.isTrace(this)) System.out.println("Database updated");
            } else {
                //if (Loggy.isTrace(this)) System.out.println("Current value retained.");
            }
        } /* while more values */

        //if (Loggy.isTrace(this)) System.out.println("All setup values have been edited & stored.");
    } /* enterConfig(String, String) */


    /**
     * Export data from the named dbobject to the specified filename
     *
     * @param   exportCmd
     * @param   fileName
     * @param   format
     * @param   myConnection
     * @throws  DBException
     * @throws  IOException
     * @throws  IllegalAccessException
     * @throws  InstantiationException
     * @throws  ClassNotFoundException
     * @throws  ServerException
     */
    private static void exportFile(String exportCmd, String fileName,
        String format, DBConnection myConnection) throws DBException,
        IOException, IllegalAccessException, InstantiationException,
        ClassNotFoundException, ServerException {

        setupLog();

        DBObject myObj = (DBObject) Class.forName(exportCmd).newInstance();
        //myObj.setConnection(myConnection);

        if (format == null) {
            format = new String("tab");
        }

        if (format.equals("tab")) {
            exportTabFile(myObj, fileName);
        } else if (format.equals("xml")) {
            exportXmlFile(myObj, fileName);
        }
    } /* exportFile(String, String, String, DBConnection) */


    /**
     * Export the named database object into the given file, where the file
     * is tab delimited, and the fields are in the order of the database
     * object's fields
     *
     * @param   myObj
     * @param   fileName
     * @throws  IOException
     * @throws  DBException
     * @throws  ServerException
     */
    private static void exportTabFile(DBObject myObj, String fileName)
        throws IOException, DBException, ServerException {

        setupLog();

        FileOutputStream fout = new FileOutputStream(fileName, false);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        PrintStream out = new PrintStream(bout);

        int lineCount = 0;
        DBObject oneObj = null;
        String fieldName = null;

        for (Iterator rc = myObj.searchAndRetrieveList().iterator();
                rc.hasNext(); ) {

            oneObj = (DBObject) rc.next();

            for (Iterator e = myObj.getFieldListIterator(); e.hasNext(); ) {
                fieldName = (String) e.next();
                out.print(oneObj.getField(fieldName) + "\t");
            } /* for each field */
            out.println("");
            lineCount++;
        } /* for each record */

        out.flush();
        out.close();

        //if (Loggy.isTrace(this)) System.out.println("Wrote " + lineCount + " records from "
        //    + myObj.getDescription() + " to " + fileName);
    } /* exportTabFile(DBObject, String) */


    /**
     *
     *
     * @param   myObj
     * @param   fileName
     * @throws  ServerException
     */
    private static void exportXmlFile(DBObject myObj, String fileName)
        throws ServerException {

        setupLog();

        String myName = new String(thisClass + "exportXmlFile(DBObject, "
            + "String)");

        throw new ServerException(myName + ":XML export not yet implemented.");
    } /* exportXmlFile(DBObject, String) */

    /**
     * Recursively generate a Java DBObject file for every table
         * in the specified database.
         *
         * @param   myConnection
         * @throws  IOException
         * @throws  DBException
         * @throws  SQLException
     */
    private static void genAllDBObject(DBConnection myConnection)
        throws IOException, DBException, SQLException {
        carga_data();
        //if (Loggy.isTrace(this)) System.out.println("Fetching all table names to generate JAVA source files.");
        String tableName = null;
        DatabaseMetaData dm = myConnection.getDBMetaData();
        ResultSet rs = dm.getTables(null, null, null, new String[] {"TABLE"});
        //if (Loggy.isTrace(this)) System.out.println("Table names obtained. Beginning JAVA source file generation.");
        while (rs.next()) {
            tableName = rs.getString("TABLE_NAME");
            if(pasaTabla(tableName)){
	            //if (Loggy.isTrace(this)) System.out.println("Generating JAVA file for table = " + tableName);
	            genDBObject(myConnection, tableName);
            }
        }

    } /* genAllDBObjects(DBConnection) */


	private static String formatField(String tableName){
		StringBuffer tableNameFormated = new StringBuffer();
		String aux = null;
		boolean mayus = true;
		for(int i = 0; i < tableName.length(); i++){
			aux = tableName.substring(i, i + 1);
			if(aux.compareTo("_") != 0){
				if(mayus){
					tableNameFormated.append(aux.toUpperCase());
					mayus = false;
				}else{
					tableNameFormated.append(aux.toLowerCase());
				}
			}else{
				mayus = true;
			}
		}
		return "Dbo" + tableNameFormated.toString().trim();
	}
    /**
     * Generate a Java file as a database object for the given table
     *
     * @param   myConnection
     * @param   generate
     * @throws  IOException
     * @throws  DBException
     * @throws  SQLException
     */
    private static void genDBObject(DBConnection myConnection, String generate)
        throws IOException, DBException, SQLException {

        setupLog();

        DatabaseMetaData dm = myConnection.getDBMetaData();
        String thisCatalog = myConnection.getCatalog();
        //if (isTrace(this)) System.out.println("Catalog:" + thisCatalog);

        String myName = new String(thisClass
            + "genDBObject(DBConnection, String)");

        String tableName = generate;
		
		//cjvc *
		String pkcjvc = null;
		boolean cheqcjvc = false;

		
            	for(int i = 0; i < autoInc.length; i++){
            		if(tableName.equalsIgnoreCase(autoInc[i])){
				        ResultSet rspk = dm.getPrimaryKeys(null, "%", tableName);
				        while (rspk.next()) {
							pkcjvc = rspk.getString(4);
							cheqcjvc = true;
				        }
            		}
            	}

        //cjvc *    
        FileOutputStream fout = new FileOutputStream(formatField(generate) + ".java", false);
        BufferedOutputStream bout = new BufferedOutputStream(fout);
        PrintStream f = new PrintStream(bout);

        f.println("/*");
        f.println("* " + formatField(generate) + ".java - cjvc");
        f.println("*/");
        f.println("");
        f.println("package " + paquet + ";");
        f.println("");
        f.println("import com.jcorporate.expresso.core.dbobj.*;");
        f.println("import com.jcorporate.expresso.core.db.*;");
        f.println("import java.util.Hashtable;");
        f.println("import java.util.Enumeration;");
        f.println("");
        f.println("public class " + formatField(generate) + " extends DBObject {");
        f.println("");
        //cjvc
        ResultSet rs1 = dm.getColumns(null, "%", tableName, "%");
        while (rs1.next()) {
        	String fieldName = rs1.getString(4);
	        f.println("\tpublic static final String CAMPO_" + fieldName + " = \"" + fieldName + "\";");
        }	
        //cjvc
        f.println("");        
        f.println("\tpublic " + formatField(generate) + "() throws DBException {");
        f.println("\t\tsuper();");
        f.println("\t} /* " + formatField(generate) + "() */");
        f.println("");
        f.println("");
        f.println("\tpublic " + formatField(generate) + "(DBConnection theConnection) "
            + "throws DBException {");
        f.println("\t\tsuper(theConnection);");
        f.println("\t} /* " + formatField(generate) + "(DBConnection) */");
        f.println("");
        f.println("");
        f.println("\tpublic " + formatField(generate) + "(DBConnection theConnection, "
            + "String theUser) throws DBException {");
        f.println("\t\tsuper(theConnection);");
        f.println("\t} /* " + generate + "(DBConnection, String) */");
        f.println("");
        f.println("");
        f.println("\tprotected synchronized void setupFields() throws "
            + "DBException {");
        f.println("\t\tsetTargetTable(\"" + tableName + "\");");
        f.println("");
        f.println("\t\tsetDescription(\"Object Description Goes Here\");");
        f.println("");

        ResultSet rs = dm.getColumns(null, "%", tableName, "%");
        String fieldName = null;
        String fieldType;
        int fieldSize;
        int nullField = 0;
        boolean nullable;
        String fieldDescrip = null;
		//cjvc *
		boolean t1 = false;
		boolean t2 = false;
		//cjvc *
		
        while (rs.next()) {
            fieldName = rs.getString(4);
            fieldSize = rs.getInt(7);
            fieldDescrip = rs.getString(12);
            fieldType = rs.getString(6);
            nullField = rs.getInt(11);
            if (nullField == DatabaseMetaData.columnNoNulls) {
                nullable = false;
            } else {
                nullable = true;
            }
            
            //cjvc *
            if(cheqcjvc){
            	if(fieldName.equalsIgnoreCase(pkcjvc)){
            		fieldType = "auto-inc";
					fieldSize = 0;
					cheqcjvc = false;
            	}
            }
            
            //cjvc *
            
//   		   		if(tableName.equalsIgnoreCase("ESQUELA") && fieldName.equalsIgnoreCase("ESQUELA_ID")){
//            		fieldType = "auto-inc";
//					fieldSize = 0;
//					cheqcjvc = false;
//   		   		}
	   				
		   		
            //cjvc
            if(fieldType.equalsIgnoreCase("varchar2"))
            	fieldType = "VARCHAR";
            	
            f.print("\t\taddField(\"" + fieldName + "\",\"" + fieldType);
            f.print("\", " + fieldSize + ", ");
            if (nullable) {
                f.print("true");
            } else {
                f.print("false");
            }
			//HT_noviembre_se coloca fieldName para que se vea el
			//nombre de campo como su descripcion
            f.println(", \"CAMPO_" + fieldName + "\");");
        } /* for each field */

        f.println("");

        ResultSet rspk = dm.getPrimaryKeys(null, "%", tableName);

        while (rspk.next()) {
            f.println("\t\taddKey(\"" + rspk.getString(4) + "\");");
        }

        f.println("\t} /* setupFields() */");
        f.println("");
        f.println("");
        f.println("\tpublic DBObject getThisDBObj() throws DBException {");
        f.println("        return new " + formatField(generate) + "();");
        f.println("\t} /* getThisDBObj() */");
        f.println("} /* " + formatField(generate) + " */");
        f.println("");
        f.close();
    } /* genDBObject(DBConnection, String) */



    /**
     * Reverse engineer the table for each db object for every known schema, then
     * compare the fields in the database to the fields in the db object and report
     * on the differences.
     *
     */
    public static void compareTables(Page myPage,
        Vector allSchemas, String dbName) throws HtmlException, DBException,
        ConfigurationException {

        setupLog();

        String myName = new String(thisClass + "createTables(Page)");
                String defaultDbName = dbName;

        ArrayList messages = new ArrayList();

        messages.add("Comparing tables for database '" + dbName + "'");

        boolean compareLengths = true;
        /* Some drivers can't provide length information, notably Hsql */
        if (StringUtil.notNull(
            ConfigManager.getJdbcRequired(dbName).getDriver()).equals(
            "org.hsqldb.jdbcDriver")) {
            compareLengths = false;
        }

         DBConnectionPool myPool = null;
         DBConnection myConnection = null;

        try {

            /* For each schema */
            for (Enumeration e = allSchemas.elements(); e.hasMoreElements(); ) {
                Schema oneSchema = (Schema) e.nextElement();

                messages.add("Schema:" + oneSchema.getClass().getName());
                if (log.isDebugEnabled()) {
                    log.debug("Schema:" + oneSchema.getClass().getName());
                }


                /* for each database object in the schema */
                DBObject oneDBObject = null;
                for (Enumeration dbo = oneSchema.getMembers();
                        dbo.hasMoreElements(); ) {

                    oneDBObject = (DBObject) dbo.nextElement();

                    if (log.isDebugEnabled()) {
                        log.debug("DBObject:"
                            + oneDBObject.getClass().getName());
                    }
                    oneDBObject.setDBName(defaultDbName);
                    //STOP HERE

                    //This dbobject may be in an "otherdb" location, so we get it
                    //back to see...
                    dbName = oneDBObject.getDBName();

                    myPool = DBConnectionPool.getInstance(dbName);
                    myConnection = myPool.getConnection();

                    DatabaseMetaData dm = myConnection.getDBMetaData();
                    String thisCatalog = myConnection.getCatalog();
                    //messages.add("Catalog:" + thisCatalog);

                    ArrayList allFieldsFromObj = oneDBObject.getFieldListArray();
                    ArrayList allFields = new ArrayList();
                    String oneFieldName = null;
                    for (Iterator of = allFieldsFromObj.iterator(); of.hasNext(); ) {
                        oneFieldName = (String) of.next();
                        allFields.add(oneFieldName.toLowerCase());
                    }

                    String tableName = oneDBObject.getTargetTable();

                    ResultSet rs = dm.getColumns(null, "%", tableName, "%");
                    String fieldName = null;
                    String fieldType;
                    int fieldSize;
                    int nullField = 0;
                    boolean nullable;
                    String fieldDescrip = null;
                    String nullableByDBObj = null;

                    while (rs.next()) {

                        fieldName = rs.getString(4).toLowerCase();
                        fieldSize = rs.getInt(7);
                        fieldDescrip = rs.getString(12);
                        fieldType = rs.getString(6);
                        nullField = rs.getInt(11);
                        if (nullField == DatabaseMetaData.columnNoNulls) {
                            nullable = false;
                        } else {
                            nullable = true;
                        }

                        if (log.isDebugEnabled()) {
                            log.debug("Field:" + fieldName);
                        }

                        /* Now compare */
                        if (oneDBObject.isField(fieldName)) {

                            nullableByDBObj = "N";
                            if (oneDBObject.allowsNull(fieldName)) {
                                nullableByDBObj = "Y";
                            }
                            /* Drop it from the list of field names, so we know we've */
                            /* done it */

                            if (compareLengths) {
                                int dbObjLength = oneDBObject.getLengthInt(fieldName);
                                if ((dbObjLength != fieldSize) && (dbObjLength != 0)) {
                                    messages.add("Length does not match: Field '"
                                    + fieldName
                                    + "' in table '" + tableName
                                    + "' is defined as length " + fieldSize + ", "
                                    + " but in DBObject '"
                                    + oneDBObject.getClass().getName()
                                    + "' is defined as type "
                                    + oneDBObject.getType(fieldName)
                                    + " size " + dbObjLength
                                    + " and null allowed = " + nullableByDBObj
                                    + ". The table should be "
                                    + " altered to match.");
                                }
                            }
                            if (nullableByDBObj.equals("N") && nullable) {
                                messages.add("Field '"
                                + fieldName + "' in table '" + tableName
                                + "' allows nulls in the database, but the "
                                + oneDBObject.getClass().getName()
                                + " DBObject does not allow nulls for this "
                                + " field. It should be set to not allow "
                                + " nulls in the database as well."
                                + " It is defined as type "
                                + oneDBObject.getType(fieldName)
                                + ", length "
                                + oneDBObject.getLength(fieldName)
                                + ".");
                            }
                            if (nullableByDBObj.equals("Y") && (!nullable)) {
                                messages.add("Field '"
                                + fieldName + "' in table '" + tableName
                                + "' does not allow nulls in the database, "
                                + "but the "
                                + oneDBObject.getClass().getName()
                                + " DBObject *does* allow nulls for this "
                                + " field. It should be set to allow "
                                + " nulls in the database as well."
                                + " It is defined as type "
                                + oneDBObject.getType(fieldName)
                                + ", length "
                                + oneDBObject.getLength(fieldName)
                                + ".");
                            }
                            allFields.remove(fieldName);
                        } else {
                            messages.add("Field '" + fieldName
                                + "' is in the table '" + tableName
                                + "', but is not "
                                + "a field in DBObject '"
                                + oneDBObject.getClass().getName()
                                + "'. It should be "
                                + "dropped from the table.");
                        }

                    } /* for each field */

                    String oneLeftOverFieldName = null;
                    for (Iterator leftOvers = allFields.iterator();
                            leftOvers.hasNext(); ) {
                        oneLeftOverFieldName = (String) leftOvers.next();
                        if (!oneDBObject.isVirtual(oneLeftOverFieldName)) {
                            messages.add("Field '" + oneLeftOverFieldName
                                + "' is defined in "
                                + "db object '"
                                + oneDBObject.getClass().getName() + "', but "
                                + "is not defined as part of the table '"
                                + oneDBObject.getTargetTable()
                                + "' in the database. You "
                                + "must alter this table to add this field."
                                + " It is defined as type "
                                + oneDBObject.getType(oneLeftOverFieldName)
                                + ", length "
                                + oneDBObject.getLength(oneLeftOverFieldName)
                                + " and allows null = " + nullableByDBObj
                                + ".");
                        }
                    }
                if (myPool != null) {
                   myPool.release(myConnection);
                }
                myPool = null;
                } /* for each DB Object in this schema */
            } /* for each schema */
        } catch (SQLException se) {
            throw new DBException(se);
        } finally {
                if (myPool != null) {
                   myPool.release(myConnection);
                }
        }

        String oneMessage = null;
        for (Iterator i = messages.iterator(); i.hasNext(); ) {
            oneMessage = (String) i.next();
            if (myPage != null) {
                myPage.add(new Paragraph(new Text(oneMessage)));
            } else {
                //if (Loggy.isTrace(this)) System.out.println(oneMessage);
            }
        } /* for each message */

    } /* compareTables(DBConnection, String) */


    /**
     * Instantiate & load all Schema objects that we know about,
     * get a list of their DBObjects, and call the import or export
     * for each DBObject as appropriate
     *
     * @param   myConnection
     * @param   choice
     * @param   format
     * @throws  DBException
     * @throws  IllegalAccessException
     * @throws  InstantiationException
     * @throws  ClassNotFoundException
     * @throws  IOException
     * @throws  ServerException
     */
    private static void importExportAll(DBConnection myConnection,
        String choice, String format) throws DBException,
        IllegalAccessException, InstantiationException,    ClassNotFoundException,
        IOException, ServerException {

        setupLog();


        String extension;
        if (format == null) {
            format = new String("tab");
        }
        if (format.equals("xml")) {
            extension = new String("xml");
        } else {
            extension = new String("dat");
        }

        Vector allSchemas = new Vector(5);
        {    ExpressoSchema jcs = new ExpressoSchema();
            allSchemas.addElement(jcs);

            SchemaList sl = new SchemaList(SecuredDBObject.SYSTEM_ACCOUNT);
            SchemaList oneSchema = null;

            for (Enumeration e = sl.searchAndRetrieve().elements();
                e.hasMoreElements(); ) {

                oneSchema = (SchemaList) e.nextElement();

                Schema mySchema2 = (Schema) Class.forName(
                    oneSchema.getField("SchemaClass")).newInstance();
                allSchemas.addElement(mySchema2);
            } /* for each registered schema in this db */
        }

        //if (Loggy.isTrace(this)) System.out.println("Loaded " + allSchemas.size() + " schema objects.");

        Schema thisSchema = null;

        for (Enumeration als = allSchemas.elements(); als.hasMoreElements(); ) {
            thisSchema = (Schema) als.nextElement();
            //if (Loggy.isTrace(this)) System.out.println(choice + "ing objects in Schema "
            //    + thisSchema.getClass().getName());

            DBObject oneObject = null;
            for (Enumeration dbo = thisSchema.getMembers();
                dbo.hasMoreElements(); ) {
                oneObject = (DBObject) dbo.nextElement();
                //if (Loggy.isTrace(this)) System.out.println(choice + "ing database object "
                //    + oneObject.getName() + " (" + oneObject.getDescription()
                //    + ")");
                //oneObject.setConnection(myConnection);
                if (choice.equals("import")) {
                    importFile(oneObject.getClass().getName(),
                        oneObject.getTargetTable() + "." + extension,
                        format, myConnection);
                } else {
                    exportFile(oneObject.getClass().getName(),
                        oneObject.getTargetTable() + "." + extension,
                        format, myConnection);
                }
            } /* for each database object */
        } /* for each schema in the list */
    } /* importExportAll(DBConnection, String, String) */


    /**
     * Import data into the named dbobject from the specified filename
     *
     * @param   importCmd
     * @param   fileName
     * @param   format
     * @param   myConnection
     * @throws  DBException
     * @throws  IOException
     * @throws  IllegalAccessException
     * @throws  InstantiationException
     * @throws  ClassNotFoundException
     * @throws  ServerException
     */
    private static void importFile(String importCmd, String fileName,
        String format, DBConnection myConnection)
        throws DBException, IOException, IllegalAccessException,
        InstantiationException, ClassNotFoundException, ServerException {

        setupLog();

        DBObject myObj = (DBObject) Class.forName(importCmd).newInstance();
        //myObj.setConnection(myConnection);

        /* Clear existing data */
        //if (Loggy.isTrace(this)) System.out.println("Clearing existing data from "
        //    + myObj.getTargetTable());
        myConnection.executeUpdate("DELETE FROM " + myObj.getTargetTable());

        if (format == null) {
            format = new String("tab");
        }

        if (format.equals("tab")) {
            importTabFile(myObj, fileName);
        } else if (format.equals("xml")) {
            importXmlFile(myObj, fileName);
        }
    } /* importFile(String, String, String, DBConnection) */


    /**
     * Import the named file into the given database object, where the file
     * is tab delimited, and the fields are in the order of the database
     * object's fields
     *
     * @param   myObj
     * @param   fileName
     * @throws  IOException
     * @throws  DBException
     * @throws  ServerException
     */
    private static void importTabFile(DBObject myObj, String fileName)
        throws IOException, DBException, ServerException {

        setupLog();

        FileInputStream fin = new FileInputStream(fileName);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedReader ds = new BufferedReader(new InputStreamReader(bin));

        int lineCount = 0;

        String oneLine = ds.readLine();

        while(oneLine != null) {
            importTabLine(oneLine, myObj, lineCount);
            lineCount++;
            oneLine = ds.readLine();
        }

        //if (Loggy.isTrace(this)) System.out.println("Loaded " + lineCount + " records into "
        //    + myObj.getDescription());
    } /* importTabFile(DBObject, String) */


    /**
     * Process a single line of tab-delimited data into a record for the
     * given database object, and add the object to the database
     *
     * @param   oneLine
     * @param   myObj
     * @param   lineNumber
     * @throws  DBException
     * @throws  ServerException
     */
    private static void importTabLine(String oneLine, DBObject myObj,
        int lineNumber) throws DBException, ServerException {

        setupLog();

        String fieldName = null;
        myObj.clear();

        StringTokenizer stk = new StringTokenizer(oneLine, "\t");
        for (Iterator e = myObj.getFieldListIterator(); e.hasNext(); ) {
            fieldName = (String) e.next();
            if (!stk.hasMoreTokens()) {
                throw new ServerException("Error on line " + lineNumber
                    + " of import file. No value for field '" + fieldName
                    + "'");
            }
            myObj.setField(fieldName, stk.nextToken());
        } /* for each field */

        myObj.add();
    } /* importTabLine(String, DBObject, int) */


    /**
     *
     *
     * @param   myObj
     * @param   fileName
     * @throws  ServerException
     */
    private static void importXmlFile(DBObject myObj, String fileName)
        throws ServerException {
        String myName = new String(thisClass + "importXmlFile(DBObject, "
            + "String)");

        setupLog();

        throw new ServerException(myName + ":XML import not yet implemented.");
    } /* importXmlFile(DBObject, String) */

 
    /**
     * Main method so that DBTool can be launched from a command line
     *
     * @param    args[] Command line arguments to supply the information to
     *             connect to the database
     */
    public static void main(String xargs[]) 
    {
    /*
    HT nov 2002
    modificado para que genere DBObjects
    Indicando los argumentos
    
    Ejemplo si se quiere generar solamente un dbobject:
    
	    "generate=AUD_BUSQ_REG_PREDIOS",
	    
	Como generar TODOS los dbobjects:
	
		"generate=all",
    
    */
    String args[] = {
    "configDir=D:\\WSAD\\workspace\\SunarpExtranetWeb\\webApplication\\WEB-INF\\config",
	"webAppDir=D:\\WSAD\\workspace\\SunarpExtranetWeb\\webApplication",  
	"dbname=default",
	"generate=TM_GRUPO",
	"package=gob.pe.sunarp.extranet.dbobj"
    };
        String myName = new String(thisClass + "main(String[])");
		Hashtable commandArgs = new Hashtable(10);
        String paramCode = null;
        String paramValue = null;

        for (int i = 0; i < args.length; i++) {
            StringTokenizer stk = new StringTokenizer(args[i], "=");
            if (!stk.hasMoreTokens()) {
                usage();
            }
            paramCode = stk.nextToken();
            if (!stk.hasMoreTokens()) {
                usage();
            }
            paramValue = stk.nextToken();
            commandArgs.put(paramCode, paramValue);
        } /* for each command-line argument */

        try {
            if (commandArgs.get("configDir") == null) {
                usage();
                System.exit(0);
            }

            if (commandArgs.get("package") == null) {
                usage();
                System.exit(0);
            }
			// set up the package
            paquet = (String) commandArgs.get("package");

            // set up ConfigManager first
            ConfigManager.setWebAppDir((String) commandArgs.get("webAppDir"));
            ConfigManager.load((String) commandArgs.get("configDir"));
            setupLog();
            //initialize the db pool
            ConfigManager.dbInitialize();
                        //Load DBOtherMaps
                        ConfigManager.mapOtherDBs();

            //call the LoadLists method and load up the lists used by this application

            dbName = StringUtil.notNull((String) commandArgs.get("db"));
            if (dbName.equals("")) {
                dbName = "default";
            }

            String setup = (String) commandArgs.get("setup");
            String poolTest = (String) commandArgs.get("pooltest");
            String testMode = (String) commandArgs.get("test");
            String generate = (String) commandArgs.get("generate");
            String fileName = (String) commandArgs.get("file");
            String importCmd = (String) commandArgs.get("import");
            String exportCmd = (String) commandArgs.get("export");
            String format = (String) commandArgs.get("format");
            String updPassword = (String) commandArgs.get("passwords");
            String benchMark = (String) commandArgs.get("bench");
            String config = (String) commandArgs.get("config");
            String nameChange = (String) commandArgs.get("nameChange");
            String uidChange = (String) commandArgs.get("uidChange");


            if (setup != null) {
                setup(dbName);
                System.exit(1);
            }

            if (config != null) {
                chooseConfig(dbName);
                System.exit(1);
            }

            if (nameChange != null) {
                nameChange(dbName);
                System.exit(1);
            }

            if (benchMark != null) {
                doBenchMarks();
                System.exit(1);
            }

            if (uidChange != null) {
                doUidChange();
                System.exit(1);
            }

            DBConnectionPool myPool = DBConnectionPool.getInstance(dbName);

            if (poolTest != null) {
                //if (Loggy.isTrace(this)) System.out.println("Database Connection Pool Test Mode");
                poolTest();
                //if (Loggy.isTrace(this)) System.out.println("Connection pool tests completed.");
                System.exit(1);
            }

            DBConnection myConnection = myPool.getConnection("DBTool");

            if (testMode != null) {
                //if (Loggy.isTrace(this)) System.out.println("Database/Database Object Test Mode");
                testAllSchemas(myConnection);
                //if (Loggy.isTrace(this)) System.out.println("All tests completed.");
            }

            if (generate != null) {
                //if (Loggy.isTrace(this)) System.out.println("Generate DBObject Mode");
                if ("ALL".equalsIgnoreCase(generate)) {
                   //We are generating source files for all database tables.
                   genAllDBObject(myConnection);
                }
                    else {
						genDBObject(myConnection, generate);
					}
            }

            if (importCmd != null) {
                //if (Loggy.isTrace(this)) System.out.println("Import Data Mode");

                if (importCmd.equalsIgnoreCase("ALL")) {
                    importExportAll(myConnection, "import", format);
                }
                if (fileName == null) {
                    throw new ServerException(myName + ":You must supply a "
                        + "file=xxx argument for import.");
                }
                importFile(importCmd, fileName, format, myConnection);
            }

            if (exportCmd != null) {
                //if (Loggy.isTrace(this)) System.out.println("Export Data Mode");

                if (exportCmd.equalsIgnoreCase("ALL")) {
                    importExportAll(myConnection, "export", format);
                } else {
                    if (fileName == null) {
                        throw new ServerException(myName + ":You must supply a "
                            + "file=xxx argument for export.");
                    }
                    exportFile(exportCmd, fileName, format, myConnection);
                }
            } /* if export */


            myPool.release(myConnection);

        } catch(Exception de) {
            //if (Loggy.isTrace(this)) System.out.println("DBTool Error:" + de.getMessage());
            de.printStackTrace();
            System.exit(1);
        }
    } /* main(String) */


    private static void doUidChange() throws DBException {
        //if (Loggy.isTrace(this)) System.out.println("Processing UID updates from old user table");

        UserDBObj oldList = new UserDBObj();
        oldList.setDBName(dbName);
        DefaultUserInfo oneNew = null;

        UserDBObj oneOld = null;
        for (Enumeration old = oldList.searchAndRetrieve().elements(); old.hasMoreElements(); ) {
            oneOld = (UserDBObj) old.nextElement();

            //if (Loggy.isTrace(this)) System.out.println("Processing " + oneOld.getField("UserName"));

             oneNew = new DefaultUserInfo();
             oneNew.setDBName(dbName);
             oneNew.setField("LoginName", oneOld.getField("UserName"));
             if (!oneNew.find()) {
                 oneNew.setField("LoginName", oneOld.getField("UserName"));
                 oneNew.setField("UserName", oneOld.getField("Descrip"));
                 oneNew.setField("Passwd", oneOld.getField("Passwd"));
                 oneNew.setField("EMail", oneOld.getField("EMail"));
                 oneNew.setField("AccountStatus", oneOld.getField("AccountStatus"));
                 oneNew.setField("EmailValCode", oneOld.getField("EmailValCode"));
                 oneNew.setField("CreateDate", oneOld.getField("CreateDate"));
                 oneNew.setField("RegDomId", "1");
                 oneNew.setField("RegComplete", "Y");

                 try {
                     oneNew.add();
                 } catch (DBException de) {
                     de.printStackTrace(System.out);
                 }
             }
             String newId = oneNew.getField("ExpUid");

             if (!newId.equals("")) {
                 GroupMembers memberList = new GroupMembers();
                 memberList.setDBName(dbName);
                 memberList.setField("UserName", oneNew.getField("LoginName"));

                 GroupMembers oneMember = null;

                 for (Enumeration ml = memberList.searchAndRetrieve().elements(); ml.hasMoreElements(); ) {
                     oneMember = (GroupMembers) ml.nextElement();

                     oneMember.setField("ExpUid", newId);
                     oneMember.setCheckZeroUpdate(false);
                     oneMember.update();
                 }
             }
        }
    } /* doUidChange() */


    /**
     *
     *
     * @param   dbName
     * @throws  DBException
     * @throws  HtmlException
     */
    private static void nameChange(String dbName)
        throws DBException, HtmlException {

        setupLog();

        String myName = new String(thisClass + "nameChange(String)");

        //if (Loggy.isTrace(this)) System.out.println("Beginning name change update...");
        ControllerSecurity secList = new ControllerSecurity();
        secList.setDBName(dbName);

        String currClass = null;
        int updateCount = 0;

        ControllerSecurity oneSec = null;
        ControllerSecurity newSec = null;
        String newClass = null;

        for (Enumeration e = secList.searchAndRetrieve().elements();
            e.hasMoreElements(); ) {

            oneSec = (ControllerSecurity) e.nextElement();
            currClass = oneSec.getField("ControllerClass");

            if (currClass.indexOf(".trx.") > 0) {
                newSec = new ControllerSecurity();
                newSec.setDBName(dbName);

                newClass = StringUtil.replace(currClass, ".trx.",
                    ".controller.");
                newSec.setField("GroupName", oneSec.getField("GroupName"));

                if (!newSec.find()) {
                    newSec.setField("States", oneSec.getField("States"));
                    newSec.setField("GroupName", oneSec.getField("GroupName"));
                    newSec.setField("ControllerClass", newClass);
                    newSec.add();
                }
                oneSec.delete();
                updateCount++;
            }
        }

        //if (Loggy.isTrace(this)) System.out.println("\n" + updateCount + " entries changed");
    } /* nameChange(String) */


    /**
     *
     *
     * @throws  DBException
     */
    private static void poolTest() throws DBException {
        //if (Loggy.isTrace(this)) System.out.println("Begin connection pool tests:"
        //    + DateTime.getDateTimeString());
        DBConnectionPool myPool = DBConnectionPool.getInstance();

        setupLog();

        for (int i = 0; i <= 5000; i++) {
            DBConnection conn1 = myPool.getConnection("Test 1");
            DBConnection conn2 = myPool.getConnection("Test 2");
            DBConnection conn3 = myPool.getConnection("Test 3");
            DBConnection conn4 = myPool.getConnection("Test 4");
            DBConnection conn5 = myPool.getConnection("Test 5");
            myPool.release(conn1);
            myPool.release(conn2);
            myPool.release(conn3);
            myPool.release(conn4);
            myPool.release(conn5);
        }
        //if (Loggy.isTrace(this)) System.out.println("End connection pool tests:"
        //    + DateTime.getDateTimeString());
    } /* poolTest() */


    /**
     *
     *
     * @param   dbName
     * @throws  DBException
     * @throws  HtmlException
     */
    private static void setup(String dbName) throws DBException, HtmlException, ConfigurationException {
        String myName = new String(thisClass + "setup(String)");

        setupLog();

        //if (Loggy.isTrace(this)) System.out.println("Beginning system setup...");
        if (StringUtil.notNull(dbName).equals("")) {
            dbName = "default";
        }

        Vector allSchemas = new Vector(5);
        ExpressoSchema jcs = new ExpressoSchema();
        allSchemas.addElement(jcs);

        SchemaList sl = null;
        SchemaList oneSchema = null;
        boolean noOtherSchemas = true;

        try {
            sl = new SchemaList(SecuredDBObject.SYSTEM_ACCOUNT);
            sl.setDBName(dbName);
            if (sl.count() > 0) {
                noOtherSchemas = false;
            }
        } catch (DBException de) {
            noOtherSchemas = true;
        }


        if (!noOtherSchemas) {
            for (Enumeration e = sl.searchAndRetrieve().elements();
                e.hasMoreElements(); ) {

                oneSchema = (SchemaList) e.nextElement();
                boolean schemaWarning = false;

                try    {
                    Schema mySchema2 = (Schema) Class.forName(
                        oneSchema.getField("SchemaClass")).newInstance();
                    allSchemas.addElement(mySchema2);
                    //if (Loggy.isTrace(this)) System.out.println("Adding Schema "
                    //    + oneSchema.getField("SchemaClass"));
                } catch(IllegalAccessException ie) {
                    schemaWarning = true;
                    System.err.println(myName
                        + Messages.getString("_Illegal_Access_")
                        + Messages.getString("Exception_starting_Schema_")
                        + oneSchema.getField("SchemaClass")    + ":"
                        + ie.getMessage());
                } catch(InstantiationException ie) {
                    schemaWarning = true;
                    System.err.println(myName
                        + Messages.getString("_Can't_instantiate_")
                        + "Schema class " + oneSchema.getField("SchemaClass")
                        + ":" + ie.getMessage());
                } catch(ClassNotFoundException se) {
                    schemaWarning = true;
                    System.err.println(myName
                        + Messages.getString("_Can't_find_a_Schema_")
                        + Messages.getString("class_called_")
                        + oneSchema.getField("SchemaClass") + ":"
                        + se.getMessage());//$NON-NLS-3$//$NON-NLS-2$
                } catch(Exception eo)    {
                    schemaWarning = true;
                    System.err.println(myName
                        + Messages.getString("_Exception_loading_")
                        + Messages.getString("Schema_-_see_detailed_mess")
                        + eo.getMessage());
                }

                if (schemaWarning) {
                    System.err.println("Warning: Unable to process schema "
                        + oneSchema.getField("SchemaClass")
                        + " - see log for details");
                }
            } /* for each registered schema in this db */
        } /* if we read other schemas */

        //if (Loggy.isTrace(this)) System.out.println("Creating tables...");
        createTables(null, allSchemas, dbName);

        //if (Loggy.isTrace(this)) System.out.println("Comaring tables...");
        compareTables(null, allSchemas, dbName);

        //if (Loggy.isTrace(this)) System.out.println("Setting up security...");
        setupSecurity(null, allSchemas, dbName);

        //if (Loggy.isTrace(this)) System.out.println("Populating default values...");
        populateTables(null, allSchemas, dbName);


        //if (Loggy.isTrace(this)) System.out.println("Other setup functions...");
        otherSetups(null, allSchemas, dbName);

        Setup.setTableExists(dbName);

        //if (Loggy.isTrace(this)) System.out.println("\nAll setups complete");
    } /* setup(String) */


    /**
     * Instantiate & load all Schema objects that we know about,
     * get a list of their DBObjects, and call the verify() method
     * of each DBObject
     *
     * @param   myConnection
     * @throws  DBException
     * @throws  IllegalAccessException
     * @throws  InstantiationException
     * @throws  ClassNotFoundException
     */
    private static void testAllSchemas(DBConnection myConnection)
        throws DBException, IllegalAccessException, InstantiationException,
        ClassNotFoundException {

        setupLog();

        //if (Loggy.isTrace(this)) System.out.println("Begin schema tests:"
        //    + DateTime.getDateTimeString());
        Vector allSchemas = new Vector(5); {
            ExpressoSchema jcs = new ExpressoSchema();
            allSchemas.addElement(jcs);

            SchemaList sl = new SchemaList(SecuredDBObject.SYSTEM_ACCOUNT);
            SchemaList oneSchema = null;

            for (Enumeration e = sl.searchAndRetrieve().elements();
                e.hasMoreElements(); ) {

                oneSchema = (SchemaList) e.nextElement();

                Schema mySchema2 = (Schema) Class.forName(
                    oneSchema.getField("SchemaClass")).newInstance();
                allSchemas.addElement(mySchema2);
            } /* for each registered schema in this db */
        }

        //if (Loggy.isTrace(this)) System.out.println("Loaded " + allSchemas.size() + " schema objects.");

        Schema thisSchema = null;

        for (Enumeration als = allSchemas.elements(); als.hasMoreElements(); ) {
            thisSchema = (Schema) als.nextElement();
            //if (Loggy.isTrace(this)) System.out.println("Verifying Schema "
            //   + thisSchema.getClass().getName());

            DBObject oneObject = null;
            for (Enumeration dbo = thisSchema.getMembers();
                dbo.hasMoreElements(); ) {
                oneObject = (DBObject) dbo.nextElement();
                //if (Loggy.isTrace(this)) System.out.println("Verifying database object "
                //    + oneObject.getName() + " (" + oneObject.getDescription()
                //    + ")");
                //oneObject.setConnection(myConnection);
                try {
                    oneObject.verify();
                } catch(DBException de) {
                    //if (Loggy.isTrace(this)) System.out.println("Error in object " + oneObject.getName()
                    //    + ":" + de.getMessage());
                    de.printStackTrace();
                }
            } /* for each database object */
        } /* for each schema in the list */
        //if (Loggy.isTrace(this)) System.out.println("End schema tests:" + DateTime.getDateTimeString());
    } /* testAllSchemas(DBConnection) */


    /**
     * Show the user a usage message
     */
    private static void usage() {
        //if (Loggy.isTrace(this)) System.out.println("Usage: DBTool configDir=directory "
        //    + "webAppDir=webdir [db=dbname] [arg=value] ... ");
        //if (Loggy.isTrace(this)) System.out.println("Where 'directory' is the directory containing "
        //    + "default.properties and other properties files (if any)");
        //if (Loggy.isTrace(this)) System.out.println("\tand webdir is the directory in which this web "
        //    + "application is installed, if used as a web-application");
        //if (Loggy.isTrace(this)) System.out.println("\tand dbname is the name of the db/context to use "
        //    + "- 'default' is used if no dbname argument is specified");
        //if (Loggy.isTrace(this)) System.out.println(" ");
        //if (Loggy.isTrace(this)) System.out.println("Optional arguments:");
        //if (Loggy.isTrace(this)) System.out.println("\tsetup=yes: Initial system setup");
        //if (Loggy.isTrace(this)) System.out.println("\tconfig=yes: Enter/edit Setup/configuration "
        //    + "values");
        //if (Loggy.isTrace(this)) System.out.println("\ttest=yes: Invoke test mode");
        //if (Loggy.isTrace(this)) System.out.println("\tgenerate=dbobjname: Generate database object "
        //    + "source code from named table in the database. Use 'all' to "
        //                + "generate source code for every table in the database.");
        //if (Loggy.isTrace(this)) System.out.println("\tpackage=packagename: set up the package you want. Be sure with the name you set.");
        //if (Loggy.isTrace(this)) System.out.println("\timport=dbobjname: Import data into named database"
        //    + " object, use 'all' to import into all known database objects "
        //    + "for which files exist.");
        //if (Loggy.isTrace(this)) System.out.println("\tfile=filename: File name to import from/export "
        //    + "to. Not required if 'all' is used for the import parameter.");
        //if (Loggy.isTrace(this)) System.out.println("\tformat=formatCode: Format for import/export. "
        //    +  "Options are xml, tab");
        //if (Loggy.isTrace(this)) System.out.println("\tbench=yes: Run benchmarks");

        System.exit(1);
    } /* usage() */


    /**
     * Create the tables required by the database objects in the list
     * of schemas.
     * Each database object knows how to create the database table it
     * requires - this
     * method goes through the list
     * of registered schemas and calls the create method for all of the
     * database objects in the schema. If the table is already there,
     * nothing is done.
     *
     * @param    pw
     * @param    allSchemas List of the schema objects
     * @param   dbName
     * @throws    HtmlException If a problem occurrs building the
     *            confirmation page
     * @throws     DBException If there is a problem creating the tables
     */
    public static synchronized void createTables(Page myPage,
        Vector allSchemas, String dbName) throws HtmlException, DBException {

        setupLog();

        String myName = new String(thisClass + "createTables(Page)");

        if (myPage != null) {
            Paragraph para = new Paragraph(new Text(
                Messages.getString("Creating_Tables_for_databa") + " '"
                + dbName + "'"));//$NON-NLS-2$
            para.setCSSClass("jc-pageheader");
            myPage.add(para);
        } else {
            //if (Loggy.isTrace(this)) System.out.println(Messages.getString("Creating_Tables_for_databa")
            //   + " " + dbName + "'");
        }

        for (Enumeration e = allSchemas.elements(); e.hasMoreElements(); ) {
            Schema oneSchema = (Schema) e.nextElement();

            Vector v = oneSchema.createAsNeeded(dbName);
            if (v.size() > 0) {
                String oneTableName = null;
                for (Enumeration et = v.elements(); et.hasMoreElements(); ) {
                    oneTableName = (String) et.nextElement();
                    if (myPage != null) {
                        myPage.add(new Paragraph(new Text(
                            Messages.getString("Created_table_")
                            + " " + oneTableName)));
                    } else {
                        //if (Loggy.isTrace(this)) System.out.println(Messages.getString("Created_table_")
                        //    + " " + oneTableName);
                    }
                }
                if (myPage != null) {
                    myPage.add(new Paragraph(new Text("Schema "
                        + oneSchema.getClass().getName()
                        + " " + Messages.getString("__Created_") + " "
                        + v.size()
                        + Messages.getString("_tables_in_database"))));
                        //$NON-NLS-2$
                }
            } else {
                if (myPage != null) {
                    myPage.add(new Paragraph(new Text(
                        Messages.getString("Schema_") + " "
                        + oneSchema.getClass().getName()
                        + " "
                        + Messages.getString("_Tables_are_all_already_in"))));
                }
            }

            DBConnectionPool.reInitialize();


        } /* for each schema */
    } /* createTables(PrintWriter, Vector, String) */



    /**
     * Method to perform any other additonal setup required by each
     * schema
     *
     * @param    pw
     * @param    allSchemas A list of all of the schema objects
     * @param   dbName
     * @throws    DBException If there is a database problem creating
     *            the new default values
     * @throws    HtmlException If there is a problem building the
     *            confirmation page
     */
    public static synchronized void otherSetups(Page myPage,
        Vector allSchemas, String dbName) throws DBException, HtmlException {

        setupLog();

        String myName = new String(thisClass + "otherSetups(Page, Vector)");

        Schema oneSchema = null;

        for (Enumeration e = allSchemas.elements(); e.hasMoreElements(); ) {
            oneSchema = (Schema) e.nextElement();

            if (myPage != null) {
                myPage.add(new Paragraph(new Text(Messages.getString(
                    "Additional_setup_on_") + " "
                    + oneSchema.getClass().getName())));
            }

            oneSchema.otherSetup(myPage, dbName);
        }

        if (myPage != null) {
            myPage.add(new Paragraph(new Text(Messages.getString(
                "All_Additional_Setup_funct"))));
        }
    } /* otherSetups(PrintWriter, Vector, String) */


    /**
     * This method goes through the schemas and populates tables with default values
     * if they do not exist.  Works closely with
     *
     * @param    pw
     * @param    allSchemas A list of the schema objects
     * @param   dbName
     * @throws    HtmlException If a problem occurrs building the HTML page
     * @throws    DBException If a problem occurrs putting the default records
     *             in the database
     * @see com.jcorporate.expresso.core.dbobj.Schema.setupDefaultValues
     */
     public static synchronized void populateTables(Page myPage,
        Vector allSchemas, String dbName) throws HtmlException, DBException {

        setupLog();

        String myName = new String(thisClass + "populateTables(Page, Vector)");

        Schema oneSchema = null;

        Vector errors = new Vector();

        for (Enumeration e = allSchemas.elements(); e.hasMoreElements(); ) {

            oneSchema = (Schema) e.nextElement();

            if (myPage != null) {
                myPage.add(new Paragraph(new Text("Populate Tables on "
                    + oneSchema.getClass().getName())));
            } else {
                //if (Loggy.isTrace(this)) System.out.println("Populate Tables on "
                //    + oneSchema.getClass().getName());
            }

            try {
                oneSchema.setupDefaultValuesWithException(dbName);
            } catch (DBException de) {
                log.error(de);
                de.printStackTrace(System.err);
                errors.addElement(de);
            }
        }

        if (errors.size() > 0) {
            if (myPage != null) {
                myPage.add(new Paragraph(new Text("" + errors.size()
                    + " error(s) were encountered when populating defaults:")));
            } else {
                //if (Loggy.isTrace(this)) System.out.println("" + errors.size()
                //    + " error(s) were encountered when populating defaults:");
            }

            Throwable oneException = null;
            for (Enumeration ee = errors.elements(); ee.hasMoreElements(); ) {
                oneException = (Throwable) ee.nextElement();
                if (myPage != null) {
                    myPage.add(new StackTrace(oneException));
                } else {
                    oneException.printStackTrace(System.out);
                }
            }
        }

        if (myPage != null) {

            myPage.add(new Paragraph(new Text(
                "All Additional Table Populations have been performed.")));
        } else {
            //if (Loggy.isTrace(this)) System.out.println(
            //    "All Additional Table Populations have been performed.");
        }
        setupConfig(myPage, allSchemas, dbName);
    } /* populateTables(PrintWriter, Vector, String) */


    /**
     * Method to set up the default Setup values for each schema.
     * Every schema object has a list of the Setup values and default
     * values for them that are required by the application that the
     * schema applies to. The default schema is done first, then
     * any other registered schema objects
     *
     * @param    myPage - The HTML page to send the output to.  May be null if
     *          you are running from command line.
     * @param    allSchemas A list of all of the schema objects
     * @param   dbName
     * @throws    DBException If there is a database problem creating
     *            the new default values
     * @throws    HtmlException If there is a problem building the
     *            confirmation page
     */
    public static synchronized void setupConfig(Page myPage,
        Vector allSchemas, String dbName) throws DBException, HtmlException {

        setupLog();

        String myName = new String(thisClass + "setupConfig(Page, Vector)");

        Schema oneSchema = null;
        Setup oneSetup = null;
        Setup findSetup = null;

        boolean showMsg = false;

        for (Enumeration e = allSchemas.elements(); e.hasMoreElements(); ) {
            oneSchema = (Schema) e.nextElement();

            for (Enumeration rl = oneSchema.getConfig().elements();
                rl.hasMoreElements(); ) {

                oneSetup = (Setup) rl.nextElement();
                oneSetup.setDBName(dbName);
                findSetup = new Setup();
                findSetup.setDBName(dbName);
                findSetup.clear();
                findSetup.setField("SetupCode", oneSetup.getField(
                    "SetupCode"));
                findSetup.setField("SchemaClass", oneSetup.getField(
                    "SchemaClass"));
                if (!findSetup.find()) {
                    oneSetup.setDBName(dbName);
                    oneSetup.add();
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString(
                            "Added_default_config_")
                            + " " + Messages.getString("value_for_") + " "
                            + oneSetup.getField("SetupCode"))));
                    }
                }
            }

        }

        if (myPage != null) {
            myPage.add(new Paragraph(new Text(Messages.getString(
                "All_Setup/Configuration_Va")
                + Messages.getString("are_set_up."))));
        }

        Setup.setTableExists(dbName);

    } /* setupConfig(PrintWriter, Vector, String) */


    /**
     * This method creates default security entries for all of the
     * database objects and controller objects for the known schemas.
     * This includes full access for a group and user called "Admin"
     *
     * @param    pw
     * @param    allSchemas A list of the schema objects
     * @param   dbName
     * @throws    HtmlException If a problem occurrs building the HTML page
     * @throws    DBException If a problem occurrs putting the security info
     *             in the database
     */
    public static synchronized void setupSecurity(
        Page myPage,
        Vector allSchemas,
        String dbName)
        throws HtmlException, DBException {

        setupLog();

        String myName = new String(thisClass + "setupSecurity(Page)");

        boolean showMsg = false;

        /* Group 'Everybody' */
        UserGroup oneGroup = new UserGroup();
        oneGroup.setDBName(dbName);

        oneGroup.setField("GroupName", "Everybody"); //$NON-NLS-2$//$NON-NLS-1$
        if (!oneGroup.find()) {
            oneGroup.setField("Descrip", Messages.getString("All_Users"));
            //$NON-NLS-2$//$NON-NLS-1$
            oneGroup.add();
        }

        /* Group 'Nobody' */
        oneGroup.clear();

        oneGroup.setField("GroupName", "Nobody"); //$NON-NLS-2$//$NON-NLS-1$
        if (!oneGroup.find()) {
            oneGroup.setField("Descrip", Messages.getString("Unknown_Users"));
            //$NON-NLS-2$//$NON-NLS-1$
            oneGroup.add();
        }

        /* Group 'NotReg' */
        oneGroup.clear();

        oneGroup.setField("GroupName", "NotReg"); //$NON-NLS-2$//$NON-NLS-1$
        if (!oneGroup.find()) {
            oneGroup.setField("Descrip", Messages.getString("NotReg_Users"));
            //$NON-NLS-2$//$NON-NLS-1$
            oneGroup.add();
        }

        /* Group 'Admin' */
        oneGroup.clear();
        oneGroup.setField(Messages.getString("GroupName"), "Admin");
        //$NON-NLS-2$ //$NON-NLS-1$
        if (!oneGroup.find()) {
            oneGroup.setField("Descrip", Messages.getString("Administrative_Users"));
            //$NON-NLS-2$//$NON-NLS-1$
            oneGroup.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(new Text(Messages.getString("Added_'Admin'_user_group") + " ")));
            } else {
                //if (Loggy.isTrace(this)) System.out.println(Messages.getString("Added_'Admin'_user_group") + " ");
            }
        }

        RegistrationDomain rd = new RegistrationDomain();
        rd.setDBName(dbName);
        rd.setField("Name", "default");
        if (!rd.find()) {
            rd.setField("Description", "Default Registration Domain");
            rd.setField("RegRequired", "Y");
            rd.setField("GroupName", "Everybody");
            rd.setField(
                "ControllerClass",
                "com.jcorporate.expresso.services.controller.Registration");
            rd.setField("RegRequired", "N");
            rd.setField("Approve", "N");
            rd.setField("UserPasswd", "Y");
            rd.setField("EmailValidate", "N");
            rd.setField("EmailAsLogin", "N");
            rd.add();
        }

        RegistrationObjectMap rdm = new RegistrationObjectMap();
        rdm.setDBName(dbName);
        rdm.setField("RegDomId", rd.getField("RegDomId"));
        rdm.setField("RegObj", "com.jcorporate.expresso.ext.dbobj.RegisteredUser");
        if (!rdm.find()) {
            rdm.setField("UidField", "ExpUid");
            rdm.setField("RecMin", "1");
            rdm.setField("RecMax", "1");
            rdm.setField("AllowEdit", "Y");
            rdm.setField("AllowDel", "N");

            rdm.setField(
                "RegFields",
                "!IsActive"
                    + ",!UserName"
                    + ",!InternalComment"
                    + ",!AddedOn"
                    + ",!UpdatedOn"
                    + ",!URL"
                    + ",!EmailPrivate"
                    + ",!Occupation"
                    + ",!Location"
                    + ",!Expertise"
                    + ",!OtherProjects"
                    + ",!Contributions"
                    + ",!Phone"
                    + ",!Testimonial"
                    + ",!IPAddress");
            rdm.add();
        }

        rd.clear();
        rd.setField("Name", "extended");
        if (!rd.find()) {
            rd.setField("Description", "Extended Registration Domain");
            rd.setField("GroupName", "Everybody");
            rd.setField(
                "ControllerClass",
                "com.jcorporate.expresso.services.controller.Registration");
            rd.setField("RegRequired", "Y");
            rd.setField("Approve", "N");
            rd.setField("UserPasswd", "Y");
            rd.setField("EmailValidate", "Y");
            rd.setField("EmailAsLogin", "N");
            rd.add();
        }

        rdm.clear();
        rdm.setField("RegDomId", rd.getField("RegDomId"));
        rdm.setField("RegObj", "com.jcorporate.expresso.ext.dbobj.regobj.Person");
        if (!rdm.find()) {
            rdm.setField("UidField", "ExpUid");
            rdm.setField("RecMin", "1");
            rdm.setField("RecMax", "1");
            rdm.setField("AllowEdit", "Y");
            rdm.setField("AllowDel", "N");
            rdm.setField("RegFields", "!ExpUid,!AddedOn,!UpdatedOn");
            rdm.add();
        }

        rdm.clear();
        rdm.setField("RegDomId", rd.getField("RegDomId"));
        rdm.setField("RegObj", "com.jcorporate.expresso.ext.dbobj.regobj.Phone");
        if (!rdm.find()) {
            rdm.setField("UidField", "ExpUid");
            rdm.setField("RecMin", "1");
            rdm.setField("RecMax", "0");
            rdm.setField("AllowEdit", "Y");
            rdm.setField("AllowDel", "N");
            rdm.setField("RegFields", "!PhoneId,!ExpUid,!AddedOn,!UpdatedOn,+Type");
            rdm.add();
        }

        rdm.clear();
        rdm.setField("RegDomId", rd.getField("RegDomId"));
        rdm.setField("RegObj", "com.jcorporate.expresso.ext.dbobj.regobj.Address");
        if (!rdm.find()) {
            rdm.setField("UidField", "ExpUid");
            rdm.setField("RecMin", "1");
            rdm.setField("RecMax", "1");
            rdm.setField("AllowEdit", "Y");
            rdm.setField("AllowDel", "N");
            rdm.setField("RegFields", "!AddressId,!ExpUid,!AddedOn,!UpdatedOn");
            rdm.add();
        }

        rdm.clear();
        rdm.setField("RegDomId", rd.getField("RegDomId"));
        rdm.setField("RegObj", "com.jcorporate.expresso.ext.dbobj.regobj.Contact");
        if (!rdm.find()) {
            rdm.setField("UidField", "ExpUid");
            rdm.setField("RecMin", "0");
            rdm.setField("RecMax", "0");
            rdm.setField("AllowEdit", "Y");
            rdm.setField("AllowDel", "N");
            rdm.setField("RegFields", "!ContactId,!ExpUid,!AddedOn,!UpdatedOn,+Type");
            rdm.add();
        }

        User oneUser = new User();
        oneUser.setDBName(dbName);

        oneUser.setLoginName("Anonymous"); //$NON-NLS-2$//$NON-NLS-1$
        if (!oneUser.find()) {
            oneUser.setUserName(Messages.getString("Anonymous_User"));
            oneUser.setEmail("none"); //$NON-NLS-2$//$NON-NLS-1$
            oneUser.setAccountStatus("D");
            oneUser.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(new Text(Messages.getString("Added_'Anonymous'_user") + " ")));
            } else {
                //if (Loggy.isTrace(this)) System.out.println(Messages.getString("Added_'Anonymous'_user") + " ");
            }
            GroupMembers oneMember = new GroupMembers();
            oneMember.setDBName(dbName);
            oneMember.setField("GroupName", "Everybody");
            oneMember.setField("ExpUid", oneUser.getUid());
            if (!oneMember.find()) {
                oneMember.add();
                if (myPage != null) {
                    showMsg = true;
                    myPage
                        .add(new Paragraph(new Text(Messages.getString("Added_'Anonymous'_user_") + " "
                    //$NON-NLS-1$
                    +Messages.getString("to_the_group_'Everybody'"))));
                } else {
                    //if (Loggy.isTrace(this)) System.out.println(
                    //    Messages.getString("Added_'Anonymous'_user_")
                    //        + " "
                    //        + Messages.getString("to_the_group_'Everybody'"));
                }
            }
        } /* if we didn't find the Anonymous user */

        oneUser.clear();
        oneUser.setLoginName("NONE");
        if (!oneUser.find()) {
            oneUser.setUserName(Messages.getString("Default_user_before_login"));
            oneUser.setEmail("none");
            oneUser.setAccountStatus("D");
            oneUser.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(new Text(Messages.getString("Added_'NONE'_user_") + " ")));
            } else {
                //if (Loggy.isTrace(this)) System.out.println(Messages.getString("Added_'NONE'_user_") + " ");
            }
        }
        GroupMembers oneMember = new GroupMembers();
        oneMember.setDBName(dbName);
        oneMember.setField("GroupName", "Nobody");

        oneMember.setField("ExpUid", oneUser.getUid());
        if (!oneMember.find()) {
            oneMember.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(
                        new Text(Messages.getString("Added_NONE'_user_to_the_'N") + " ")));
            }
        }

        oneUser.clear();
        oneUser.setLoginName("Admin");
        if (!oneUser.find()) {
            oneUser.setUserName(Messages.getString("Administrative_User"));
            oneUser.setEmail("none");
            oneUser.setAccountStatus("A");
            oneUser.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(
                        new Text(Messages.getString("Added_'Admin'_user_-_SET_A") + " ")));
            }
        }

        oneMember.clear();
        oneMember.setField("GroupName", "Admin");
        oneMember.setField("ExpUid", oneUser.getUid());
        if (!oneMember.find()) {
            oneMember.add();
            if (myPage != null) {
                showMsg = true;
                myPage.add(
                    new Paragraph(
                        new Text(Messages.getString("Added_'Admin'_user_to_the_") + " ")));
            }
        }

        DBObjSecurity oneSecurity = new DBObjSecurity();
        oneSecurity.setDBName(dbName);

        DBObject oneObj = null;
        Schema oneSchema = null;
        for (Enumeration sl = allSchemas.elements(); sl.hasMoreElements();) {
            oneSchema = (Schema) sl.nextElement();

            for (Enumeration ee = oneSchema.getMembers(); ee.hasMoreElements();) {

                if (myPage != null) {
                    showMsg = false;
                }

                oneObj = (DBObject) ee.nextElement();
                oneSecurity.setField("GroupName", "Admin");
                //$NON-NLS-2$//$NON-NLS-1$
                oneSecurity.setField("DBObjectName", oneObj.getClass().getName());
                //$NON-NLS-1$
                oneSecurity.setField("MethodCode", "A");
                //$NON-NLS-2$//$NON-NLS-1$
                if (!oneSecurity.find()) {
                    oneSecurity.add();
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString("Added_permission_for_")
                        //$NON-NLS-1$
                        +" " + Messages.getString("'Admin'_group_to_add_new_e")
                        //$NON-NLS-1$
                        +" " + oneObj.getClass().getName())));
                    }
                }
                oneSecurity.setField("MethodCode", "U"); //$NON-NLS-2$//$NON-NLS-1$
                if (!oneSecurity.find()) {
                    oneSecurity.add();
                    if (myPage != null) {
                        showMsg = true;
                        myPage
                            .add(new Paragraph(new Text(
                                Messages.getString("Added_permission_for_")
                                + " "
                                + Messages.getString("'Admin'_group_to_update_en")
                        //$NON-NLS-1$
                        +" " + oneObj.getClass().getName())));
                    }
                }
                oneSecurity.setField("MethodCode", "S"); //$NON-NLS-2$//$NON-NLS-1$
                if (!oneSecurity.find()) {
                    oneSecurity.add();
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString("Added_permission_for_")
                        //$NON-NLS-1$
                        +" " + Messages.getString("'Admin'_group_to_search_on")
                        //$NON-NLS-1$
                        +" " + oneObj.getClass().getName())));
                    }
                }
                oneSecurity.setField("MethodCode", "D"); //$NON-NLS-2$//$NON-NLS-1$
                if (!oneSecurity.find()) {
                    oneSecurity.add();
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString("Added_permission_for_")
                        //$NON-NLS-1$
                        +" " + Messages.getString("'Admin'_group_to_delete_fr")
                        //$NON-NLS-1$
                        +" '" + oneObj.getClass().getName() + "'")));
                    }
                }

            } /* for every object in the schema */

            Controller con;
            ControllerSecurity oneControllerSecurity = new ControllerSecurity(SecuredDBObject.SYSTEM_ACCOUNT);
            oneControllerSecurity.setDBName(dbName);

            if (myPage != null) {
                showMsg = false;
            }

            for (Enumeration et = oneSchema.getControllers(); et.hasMoreElements();) {

                con = (Controller) et.nextElement();
                oneControllerSecurity.clear();
                oneControllerSecurity.setField("ControllerClass", con.getClass().getName());
                oneControllerSecurity.setField("GroupName", "Admin");
                oneControllerSecurity.setField("States", "*");
                if (!oneControllerSecurity.find()) {
                    if (myPage != null) {
                        showMsg = true;
                        myPage
                            .add(new Paragraph(new Text(
                                Messages.getString("Adding_access_to_")
                                + " "
                                + Messages.getString("all_states_of_Controller_")
                                + " '"
                                + con.getTitle() //$NON-NLS-1$
                        +"'")));
                    }
                    oneControllerSecurity.add();
                }

                if (con
                    .getClass()
                    .getName()
                    .equals("com.jcorporate.expresso.services.controller.Login")) {
                    oneControllerSecurity.clear();
                    oneControllerSecurity.setField("ControllerClass", con.getClass().getName());
                    oneControllerSecurity.setField("GroupName", "Everybody");
                    oneControllerSecurity.setField("States", "*");
                    if (!oneControllerSecurity.find()) {
                        if (myPage != null) {
                            showMsg = true;
                            myPage.add(
                                new Paragraph(new Text("Adding general " + "access to Login controller")));
                        }
                        oneControllerSecurity.add();
                    }
                } /* login controller */

            } /* for each controller */

            if (myPage != null) {
                showMsg = false;
            }

            StdServlet oneServlet;
            for (Enumeration es = oneSchema.getServlets(); es.hasMoreElements();) {

                oneServlet = (StdServlet) es.nextElement();
                oneControllerSecurity.clear();
                oneControllerSecurity.setField(
                    "ControllerClass",
                    oneServlet.getClass().getName());
                oneControllerSecurity.setField("GroupName", "Admin");
                //$NON-NLS-2$//$NON-NLS-1$
                oneControllerSecurity.setField("States", "*");
                //$NON-NLS-2$//$NON-NLS-1$
                if (!oneControllerSecurity.find()) {
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString("Adding_access_to_")
                        //$NON-NLS-1$
                        +Messages.getString("Servlet_'") + " '" + oneServlet.getTitle() //$NON-NLS-1$
                        +"'")));
                    }
                    oneControllerSecurity.add();
                }
            } /* for each servlet */

            Job j;
            JobSecurity oneJobSecurity = new JobSecurity(SecuredDBObject.SYSTEM_ACCOUNT); //$NON-NLS-1$
            oneJobSecurity.setDBName(dbName);

            for (Enumeration ej = oneSchema.getJobs(); ej.hasMoreElements();) {

                j = (Job) ej.nextElement();
                oneJobSecurity.clear();
                oneJobSecurity.setField("JobClass", j.getClass().getName());
                //$NON-NLS-1$
                oneJobSecurity.setField("GroupName", "Admin");
                //$NON-NLS-2$//$NON-NLS-1$
                oneJobSecurity.setField("Functions", "*");
                //$NON-NLS-2$//$NON-NLS-1$
                if (!oneJobSecurity.find()) {
                    if (myPage != null) {
                        showMsg = true;
                        myPage.add(new Paragraph(new Text(Messages.getString("Adding_access_to_")
                        //$NON-NLS-1$
                        +" " + Messages.getString("all_functions_of_Job_'") + " '" + j.getTitle()
                        //$NON-NLS-1$
                        +"'"))); //$NON-NLS-1$
                        oneJobSecurity.add();
                    }
                } /* if job security not found */

            } /* for each job*/

        } /* for each schema */

        if (myPage != null) {
            myPage.add(
                new Paragraph(new Text(Messages.getString("Security_Checks_Complete"))));
        }
    } /* setupSecurity(PrintWriter, Vector, String) */

} /* DBTool */


