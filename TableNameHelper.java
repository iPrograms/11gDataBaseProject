/* this is a helper class to help label the buttons and execure the SQL command in the future
 * 
 */


public class TableNameHelper {
	
		private String maker;
		private String tableName;
		public TableNameHelper(String m, String tName)
		{
			maker = m;
			tableName = tName;
			
		}
		public String getCompanyName()
		{
			return maker;
		}
		public String getTableName()
		{
			return tableName;
			
		}
		
	
}
