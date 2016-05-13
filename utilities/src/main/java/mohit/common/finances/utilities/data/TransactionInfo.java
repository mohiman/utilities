package mohit.common.finances.utilities.data;

import java.io.Serializable;

public class TransactionInfo implements Serializable {

	// Stage Transaction Date Posted Date Card No. Description Category Debit
	// Credit

	private String bankOrCardName;
	private String txnStage;
	private String txnDate;
	private String postDate;
	private String accountEndingIn;
	private String description;
	private String category;
	private String amountDebited;
	private String amountCredited;

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

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getPostDate() {
		return postDate;
	}

	public void setPostDate(String postDate) {
		this.postDate = postDate;
	}

	public String getAccountEndingIn() {
		return accountEndingIn;
	}

	public void setAccountEndingIn(String accountEndingIn) {
		this.accountEndingIn = accountEndingIn;
	}

	public String getDescription() {
		return description;
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

	public String getAmountDebited() {
		return amountDebited;
	}

	public void setAmountDebited(String amountDebited) {
		this.amountDebited = amountDebited;
	}

	public String getAmountCredited() {
		return amountCredited;
	}

	public void setAmountCredited(String amountCredited) {
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

