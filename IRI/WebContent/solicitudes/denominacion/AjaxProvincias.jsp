<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>sunarp extranet provincias</title>
</head>
<body>
<%
	//Obtengo el parametro enviado por el comboBox
        String dpto = request.getParameter("cboDepartamento");
        System.out.println("Departamento:::" + dpto);
        if( dpto.equalsIgnoreCase("01") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value =00>Seleccionar</option>");
                out.print("<option value =0101>Chachapoyas</option>");
                out.print("<option value =0102>Bagua</option>");
                out.print("<option value =0103>Bongora</option>");
                out.print("<option value =0104>Luya</option>");
                out.print("<option value =0105>Rodriguez de Mendoza</option>");
                out.print("<option value =0106>Condorcanqui</option>");
                out.print("<option value =0107>Utcubamba</option>");
                out.print("</select>");
              
	}
         if( dpto.equalsIgnoreCase("02") )
	{
		
                out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0201>Huaraz</option>");
                out.print("<option value =0202>Aija</option>");
                out.print("<option value =0203>Bolognesi</option>");
                out.print("<option value =0204>Carhuaz</option>");
                out.print("<option value =0205>Corongo</option>");
                out.print("<option value =0206>Huari</option>");
                out.print("<option value =0207>Casma</option>");
                out.print("<option value =0208>Huaylas</option>");
                out.print("<option value =0209>Pallasca</option>");
                out.print("<option value =0210>Pomabamba</option>");
                out.print("<option value =0211>Recuay</option>");
                out.print("<option value =0212>Santa</option>");
                out.print("<option value =0213>Yungay</option>");
                out.print("<option value =0214>Mariscal Luzuriaga</option>");
                out.print("<option value =0215>Sihuas</option>");
                out.print("<option value =0216>Antonio Raymondi</option>");
                out.print("<option value =0217>Asuncion</option>");
                out.print("<option value =0218>Huarmey</option>");
                out.print("<option value =0219>C.Fermin Fitzcarrald</option>");
                out.print("<option value =0220>Ocros</option>");
                out.print("<option value =0221>Marañon</option>");
                out.print("</select>");

	}


                 if( dpto.equalsIgnoreCase("03") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0301>Abancay</option>");
                out.print("<option value =0302>Andahuaylas</option>");
                out.print("<option value =0303>Antabamba</option>");
                out.print("<option value =0304>Aymaraes</option>");
                out.print("<option value =0305>Grau</option>");
                out.print("<option value =0306>Cotabamba</option>");
                out.print("<option value =0307>Chincheros</option>");
                out.print("</select>");

	}

                 if( dpto.equalsIgnoreCase("04") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0401>Arequipa</option>");
                out.print("<option value =0402>Camana</option>");
                out.print("<option value =0403>Caraveli</option>");
                out.print("<option value =0404>Castilla</option>");
                out.print("<option value =0405>Caylloma</option>");
                out.print("<option value =0406>Condesuyos</option>");
                out.print("<option value =0407>Islay</option>");
                out.print("<option value =0408>La Union</option> ");
                out.print("</select>");

	}

                 if( dpto.equalsIgnoreCase("05") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0501>Huamanga</option>");
                out.print("<option value =0502>Cangallo</option>");
                out.print("<option value =0503>Huanta</option>");
                out.print("<option value =0504>La Mar</option>");
                out.print("<option value =0505>Lucanas</option>");
                out.print("<option value =0506>Parinacochas</option>");
                out.print("<option value =0507>Victor Fajardo</option>");
                out.print("<option value =0508>Paucar del Sara Sara</option>");
                out.print("<option value =0509>Sucre</option>");
                out.print("<option value =0510>Vilcashuaman</option>");
                out.print("<option value =0511>Huancasancos</option>");
                out.print("</select>");
	}

                  if( dpto.equalsIgnoreCase("06") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0601>Cajamarca</option>");
                out.print("<option value =0602>Cajabamba</option>");
                out.print("<option value =0603>Celendin</option>");
                out.print("<option value =0604>Contumaza</option>");
                out.print("<option value =0605>Cutervo</option>");
                out.print("<option value =0606>Chota</option>");
                out.print("<option value =0607>Hualgayoc</option>");
                out.print("<option value =0608>Jaen</option>");
                out.print("<option value =0609>Santa Cruz</option>");
                out.print("<option value =0610>San Miguel</option>");
                out.print("<option value =0611>San Ignacio</option>");
                out.print("<option value =0612>San Marcos</option>");
                out.print("<option value =0613>San Pablo</option>");
                out.print("</select>");

	}


                  if( dpto.equalsIgnoreCase("08") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0801>Cusco</option>");
                out.print("<option value =0802>Acomayo</option>");
                out.print("<option value =0803>Anta</option>");
                out.print("<option value =0804>Calca</option>");
                out.print("<option value =0805>Canas</option>");
                out.print("<option value =0806>Canchis</option>");
                out.print("<option value =0807>Chumbivilcas</option>");
                out.print("<option value =0808>Espinar</option>");
                out.print("<option value =0809>La Convencion</option>");
                out.print("<option value =0810>Paruro</option>");
                out.print("<option value =0811>Paucartambo</option>");
                out.print("<option value =0812>Quispicanchis</option>");
                out.print("<option value =0813>Urubamba</option>");
                out.print("</select>");
	}

                if( dpto.equalsIgnoreCase("09") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =0901>Huancavelica</option>");
                out.print("<option value =0902>Acobamba</option>");
                out.print("<option value =0903>Angaraes</option>");
                out.print("<option value =0904>Castrovirreyna</option>");
                out.print("<option value =0905>Tayacaja</option>");
                out.print("<option value =0906>Churcampa</option>");
                out.print("<option value =0907>Huaytara</option>");
                out.print("</select>");

	}
           if( dpto.equalsIgnoreCase("10") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1001>Huanuco</option>");
                out.print("<option value =1002>Ambo</option>");
                out.print("<option value =1003>Dos de Mayo</option>");
                out.print("<option value =1004>Huamalies</option>");
                out.print("<option value =1005>Marañon</option>");
                out.print("<option value =1006>pachitea</option>");
                out.print("<option value =1007>Leoncio Prado</option>");
                out.print("<option value =1008>Hucaybamba</option>");
                out.print("<option value =1009>Puerto Inca</option>");
                out.print("<option value =1010>Lauricocha</option>");
                out.print("<option value =1011>Yarowilca</option>");
                out.print("</select>");
	}

           if( dpto.equalsIgnoreCase("11") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1101>Ica</option>");
                out.print("<option value =1102>Chincha</option>");
                out.print("<option value =1103>Nazca</option>");
                out.print("<option value =1104>Pisco</option>");
                out.print("<option value =1105>Palpa</option>");
                out.print("</select>");
	}

     if( dpto.equalsIgnoreCase("12") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1201>Huancayo</option>");
                out.print("<option value =1202>Jauja</option>");
                out.print("<option value =1203>Junin</option>");
                out.print("<option value =1204>Tarma</option>");
                out.print("<option value =1205>Yauli</option>");
                out.print("<option value =1206>Concepcion</option>");
                out.print("<option value =1207>Satipo</option>");
                out.print("<option value =1208>Chanchamayo</option>");
                out.print("<option value =1209>Chupaca</option>");
                out.print("<option value =1210>Tayacaja</option>");
                out.print("</select>");
	}

     if( dpto.equalsIgnoreCase("13") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1301>Trujillo</option>");
                out.print("<option value =1302>Bolivar</option>");
                out.print("<option value =1303>Sanchez Carrion</option>");
                out.print("<option value =1304>Otuzco</option>");
                out.print("<option value =1305>Pacasmayo</option>");
                out.print("<option value =1306>Pataz</option>");
                out.print("<option value =1307>Santiago de Chuco</option>");
                out.print("<option value =1308>Ascope</option>");
                out.print("<option value =1309>Chepen</option>");
                out.print("<option value =1310>Gran Chimu</option>");
                out.print("<option value =1311>Viru - Provincia</option>");
                out.print("</select>");
	}

       if( dpto.equalsIgnoreCase("14") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1401>Chiclayo</option>");
                out.print("<option value =1402>Lambayeque</option>");
                out.print("<option value =1403>Ferreñafe</option>");
                out.print("</select>");
	}

       if( dpto.equalsIgnoreCase("15") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1500>Callao</option>");
                out.print("<option value =1501>Lima</option>");
                out.print("<option value =1502>Cajatambo</option>");
                out.print("<option value =1503>Canta</option>");
                out.print("<option value =1504>Cañete</option>");
                out.print("<option value =1505>Huaura</option>");
                out.print("<option value =1506>Huarochiri</option>");
                out.print("<option value =1507>Yauyos</option>");
                out.print("<option value =1508>Barranca</option>");
                out.print("<<option value =1509>Huaral</option>");
                out.print("<option value =1510>Oyon</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("16") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1601>Alto Amazonas</option>");
                out.print("<option value =1602>Loreto</option>");
                out.print("<option value =1603>Mrcal Ramon Castilla</option>");
                out.print("<option value =1604>Maynas</option>");
                out.print("<option value =1605>Requena</option>");
                out.print("<option value =1606>Ucayali</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("17") )
	{
		        out.print("<select>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1701>Tambopata</option>");
                out.print("<option value =1702>Manu</option>");
                out.print("<option value =1703>Tahuamanu</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("18") )
	{
		        out.print("<select>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1801>Mariscal Nieto</option>");
                out.print("<option value =1802>General Sanchez cerro</option>");
                out.print("<option value =1803>Ilo</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("19") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =1901>Pasco</option>");
                out.print("<option value =1902>Daniel Carrion</option>");
                out.print("<option value =1903>Oxapampa</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("20") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =2001>Piura</option>");
                out.print("<option value =2002>Ayabaca</option>");
                out.print("<option value =2003>Huancabamba</option>");
                out.print("<option value =2004>Morropon</option>");
                out.print("<option value =2005>Paita</option>");
                out.print("<option value =2006>Sullana</option>");
                out.print("<option value =2007>Talara</option>");
                out.print("<option value =2008>Sechura</option>");
                out.print("</select>");
	}


               if( dpto.equalsIgnoreCase("21") )
	{
		        out.print("<select>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =2101>Puno</option>");
                out.print("<option value =2102>Azangaro</option>");
                out.print("<option value =2103>Carabaya</option>");
                out.print("<option value =2104>Chuchuito</option>");
                out.print("<option value =2105>Huancane</option>");
                out.print("<option value =2106>Lampa</option>");
                out.print("<option value =2107>Melgar</option>");
                out.print("<option value =2108>Sandia</option>");
                out.print("<option value =2109>San Roman</option>");
                out.print("<option value =2110>San Antonio de Putina</option>");
                out.print("<option value =2111>Yunguyo</option>");
                out.print("<option value =2112>Moho</option>");
                out.print("<option value =2113> El Collao</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("22") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =2201>Moyobamba</option>");
                out.print("<option value =2202>Huallaga</option>");
                out.print("<option value =2203>Lamas</option>");
                out.print("<option value =2204>Mariscal Caceres</option>");
                out.print("<option value =2205>Rioja</option>");
                out.print("<option value =2206>San Martin</option>");
                out.print("<option value =2207>Huallaga</option>");
                out.print("<option value =2208>Picota</option>");
                out.print("<option value =2209>Tocache</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("23") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value=00>Seleccionar</option>");
                out.print("<option value =2301>Tacna</option>");
                out.print("<option value =2302>Tarata</option>");
                out.print("<option value =2303>Candarave</option>");
                out.print("<option value =2304>Jose Basadre</option>");
                out.print("</select>");
	}

               if( dpto.equalsIgnoreCase("24") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value =00>Seleccionar</option>");
                out.print("<option value =2401>Tumbes</option>");
                out.print("<option value =2402>Contralmirante Villar</option>");
                out.print("<option value =2403>Zarumilla</option>");
                out.print("</select>");
	}


               if( dpto.equalsIgnoreCase("25") )
	{
		        out.print("<select size=1 name=cboProvincia>");
                out.print("<option value =00>Seleccionar</option>");
                out.print("<option value =2501>Coronel Portillo</option>");
                out.print("<option value =2502>Atalaya</option>");
                out.print("<option value =2503>Purus</option>");
                out.print("<option value =2504>Padre Abad</option>");
                out.print("</select>");
	}
%>
</body>
</html>