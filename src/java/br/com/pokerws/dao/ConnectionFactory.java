package br.com.pokerws.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	private static Connection con = null;

	private static String url = "jdbc:mysql://31.220.104.175:3306/u517145271_poker??autoReconnect=true";
	private static String usuario = "u517145271_root";
	private static String senha = "poker07";
        
        
        
        //private static String url = "jdbc:mysql://localhost:3306/gerenciapoker?useSSL=false";
	//private static String usuario = "root";
	//private static String senha = "root";
        
        

	public static Connection getConnection() {
		try {

			if (con == null || con.isClosed()) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					con = DriverManager.getConnection(url, usuario, senha);

				} catch (Exception e) {
					System.out.println("N�o foi possivel conectar com o Banco de Dados");
					e.printStackTrace();

				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

		return con;
	}


}
