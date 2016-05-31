package mohit.common.finances.utilities.data;

public class AccountInfo 
{

	private String accountName;
	private String accountEmailFrom;
	
	
	//---Regular Expressions
	
	private String dateRegex;
	private String amtRegex;
	private String mrchntRegxStart;
	private String mrchntRegxEnd;
	private String mrchntRegx;
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountEmailFrom() {
		return accountEmailFrom;
	}
	public void setAccountEmailFrom(String accountEmailFrom) {
		this.accountEmailFrom = accountEmailFrom;
	}
	public String getDateRegex() {
		return dateRegex;
	}
	public void setDateRegex(String dateRegex) {
		this.dateRegex = dateRegex;
	}
	public String getAmtRegex() {
		return amtRegex;
	}
	public void setAmtRegex(String amtRegex) {
		this.amtRegex = amtRegex;
	}
	public String getMrchntRegxStart() {
		return mrchntRegxStart;
	}
	public void setMrchntRegxStart(String mrchntRegxStart) {
		this.mrchntRegxStart = mrchntRegxStart;
	}
	public String getMrchntRegxEnd() {
		return mrchntRegxEnd;
	}
	public void setMrchntRegxEnd(String mrchntRegxEnd) {
		this.mrchntRegxEnd = mrchntRegxEnd;
	}
	public String getMrchntRegx() {
		return mrchntRegx;
	}
	public void setMrchntRegx(String mrchntRegx) {
		this.mrchntRegx = mrchntRegx;
	}
	@Override
	public String toString() {
		return "AccountInfo [accountName=" + accountName + ", accountEmailFrom=" + accountEmailFrom + ", dateRegex="
				+ dateRegex + ", amtRegex=" + amtRegex + ", mrchntRegxStart=" + mrchntRegxStart + ", mrchntRegxEnd="
				+ mrchntRegxEnd + ", mrchntRegx=" + mrchntRegx + "]";
	}
	
		
	
}
