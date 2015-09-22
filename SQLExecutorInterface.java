package com.krkh.xls.util;

import java.sql.Connection;

public interface SQLExecutorInterface {
	void executeSimpleQueryForSummary(XMLObject xmlObject, Connection con);
	void executeSimpleQueryForList(XMLObject xmlObject, Connection con);
}
