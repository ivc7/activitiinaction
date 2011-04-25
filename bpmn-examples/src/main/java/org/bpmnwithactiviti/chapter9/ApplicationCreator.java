package org.bpmnwithactiviti.chapter9;

import java.util.Collection;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.bpmnwithactiviti.chapter9.LoanApplication;
import org.bpmnwithactiviti.chapter9.model.CreditCheckResult;
import org.bpmnwithactiviti.chapter9.model.LoanApplicant;

public class ApplicationCreator implements JavaDelegate {

	public void execute(DelegateExecution execution) {
		
		LoanApplicant applicant = (LoanApplicant) execution.getVariable("loanApplicant");

		LoanApplication la = new LoanApplication();
		la.setCustomerName(applicant.getName());
		la.setIncome(applicant.getIncome());
		la.setRequestedAmount(applicant.getLoanAmount());
		la.setEmailAddres(applicant.getEmailAddress());

		@SuppressWarnings("unchecked")
		Collection<Object> ruleOutputList = (Collection<Object>) execution.getVariable("rulesOutput");
		
		for (Object obj : ruleOutputList) {
			if (obj instanceof CreditCheckResult) {
				System.out.println("Setting the credit check result value in the loanApplication");
				la.setCreditCheckOk(((CreditCheckResult) obj).isCreditCheckPassed());
			}
		}
		execution.setVariable("loanApplication", la);
	}

}