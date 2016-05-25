package mohit.common.finances.utilities.data;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TransactionInfo implements Serializable {

	// Stage Transaction Date Posted Date Card No. Description Category Debit
	// Credit

	private String bankOrCardName;
	private String txnStage;
	private Date txnDate;
	private Date postDate;
	private String accountEndingIn;
	private String description;
	private String category;
	private Float amountDebited;
	private Float amountCredited;

	
	public TransactionInfo() {
		super();
	}
		public TransactionInfo(String name, String commaSeparatedString) {
			super();
		this.bankOrCardName = name;
	}

	public String getName() {
		return bankOrCardName;
	}

	public void setName(String name) {
		this.bankOrCardName = name;
	}

	public String getTxnStage() {
		return txnStage;
	}

	public void setTxnStage(String txnStage) {
		this.txnStage = txnStage;
	}

	public Date getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(Date txnDate) {
		this.txnDate = txnDate;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public String getAccountEndingIn() {
		return accountEndingIn;
	}

	public void setAccountEndingIn(String accountEndingIn) {
		this.accountEndingIn = accountEndingIn;
	}

	public String getDescription() 
	{
		return description != null && description.length()>15 ? description.substring(0,14) : description;
	}
	public String getShortDescription()
	{
		
		return description   ;
		
		
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Float getAmountDebited() {
		return amountDebited;
	}

	public void setAmountDebited(Float amountDebited) {
		this.amountDebited = amountDebited;
	}

	public Float getAmountCredited() {
		return amountCredited;
	}

	public void setAmountCredited(Float amountCredited) {
		this.amountCredited = amountCredited;
	}
	@Override
	public String toString() {
		return "TransactionInfo [bankOrCardName=" + bankOrCardName + ", txnStage=" + txnStage + ", txnDate=" + txnDate
				+ ", postDate=" + postDate + ", accountEndingIn=" + accountEndingIn + ", description=" + description
				+ ", category=" + category + ", amountDebited=" + amountDebited + ", amountCredited=" + amountCredited
				+ "]";
	}



}

