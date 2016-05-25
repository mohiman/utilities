package mohit.common.finances.utilities.data;

public class AccountInfo 
{

	private String accountName;
	private String accountEmailSubLine;
	private String accountEmailParseStartText;
	private String accountEmailParsEendText;
	private String accountEmailFrom;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountEmailSubLine() {
		return accountEmailSubLine;
	}
	public void setAccountEmailSubLine(String accountEmailSubLine) {
		this.accountEmailSubLine = accountEmailSubLine;
	}
	public String getAccountEmailParseStartText() {
		return accountEmailParseStartText;
	}
	public void setAccountEmailParseStartText(String accountEmailParseStartText) {
		this.accountEmailParseStartText = accountEmailParseStartText;
	}
	public String getAccountEmailParsEendText() {
		return accountEmailParsEendText;
	}
	public void setAccountEmailParsEendText(String accountEmailParsEendText) {
		this.accountEmailParsEendText = accountEmailParsEendText;
	}
	public String getAccountEmailFrom() {
		return accountEmailFrom;
	}
	public void setAccountEmailFrom(String accountEmailFrom) {
		this.accountEmailFrom = accountEmailFrom;
	}
	@Override
	public String toString() {
		return "AccountInfo [accountName=" + accountName + ", accountEmailSubLine=" + accountEmailSubLine
				+ ", accountEmailParseStartText=" + accountEmailParseStartText + ", accountEmailParsEendText="
				+ accountEmailParsEendText + ", accountEmailFrom=" + accountEmailFrom + "]";
	}

}
