package org.evansnet.dataconnector.internal.core;

public enum DBType {
	MS_SQLSrv,
	Oracle,
	DB2,
	Sybase,
	MySQL,
	Derby,
	SQL_Lite,
	NONE;
	
	@SuppressWarnings("static-access")
	public DBType getType(String s) {
		if (s.contains("Microsoft") || s.contains("MS")) {
			return this.MS_SQLSrv;
		} else if (s.contains("Oracle")) {
			return this.Oracle;
		} else if (s.contains("DB2")) {
			return this.DB2;
		} else if (s.contains("Sybase")) {
			return this.Sybase;
		} else if (s.contains("My")) {
			return this.MySQL;
		} else if (s.contains("Derby")) {
			return this.Derby;
		} else if(s.contains("Lite")) {
			return this.SQL_Lite;
		} 
		
		return this.NONE; // No match for input string.
	}
}