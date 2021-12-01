package com.saib.services;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import com.saib.config.ApiSuccessPayload;
import com.saib.models.Account;
import com.saib.models.Transaction;
import com.saib.repository.TransactionRepository;
import com.saib.util.Results;

import io.sentry.Sentry;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepository transactionRepository;
	
	

	public List<Transaction> getAllTransactions()
	{
		List<Transaction> list=transactionRepository.findAll();
		return list;
	}
		
	
	public Transaction getTransactionByTransactionNumber(long transactionNumber)
	{
		Optional<Transaction> optional=transactionRepository.findById(transactionNumber);
		
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transaction with Transaction Number:"+transactionNumber+"doesn't exist");
		}
		
	}
	
	public List<Transaction> getTransactionsByType(String type)
	{
		List<Transaction> list=transactionRepository.findByTransactionType(type);
		if(!list.isEmpty())
			return list;
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transaction With type " + type + " does not exits");
		}
		
		
	}
	
	public String addTransaction(Transaction transaction)
	{
		String result="";
		Transaction storedTransaction=transactionRepository.save(transaction);
		if(storedTransaction!=null) {
			result=Results.SUCCESS;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transaction not created");
		}
		
		return result;
	}
	
	public String updateTransaction(Transaction transaction, long transactionNumber)
	{
		String result="";
		
		transaction.setTransactionId(transactionNumber);
		Transaction updatedTransaction=transactionRepository.save(transaction);
		
		if(updatedTransaction!=null)
		{
			result=Results.SUCCESS;
		}
		else
		{
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Record was not updated");
		}
		return result;
		
	}
	
	public String deleteTransaction(long transactionNumber)
	{
		String result="";
		try {
		transactionRepository.deleteById(transactionNumber);
		
		
			result=Results.SUCCESS;
			return result;
		}
		catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		
	}
	public List<Transaction> getAllTransactions(int pageNo,int pageSize)
	{
		Pageable paging=PageRequest.of(pageNo,pageSize);
	
		
		Page<Transaction> pagedResult=transactionRepository.findAll(paging);
		int totalElements=pagedResult.getNumberOfElements();
		int total=pagedResult.getTotalPages();
		System.out.println("Total Number of Pages are:"+total+" | Total Elements:"+totalElements);
		
		if(pagedResult.hasContent())
		{
			return pagedResult.getContent();
		}
		else
		{
			return new ArrayList<Transaction>();
		}
		
	}
	
public List<Transaction> getAllTransactions(int pageNo, int pageSize, String sortBy) {


		
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Transaction> pageResult=transactionRepository.findAll(paging);
		int total = pageResult.getTotalPages();
		System.out.println(total);
		if(pageResult.hasContent()) {
			return pageResult.getContent();
		}
		else
			return new ArrayList<Transaction>();
		
	}


	public List<Transaction> getTransactionsByDate(LocalDate date) {
		List<Transaction> list =transactionRepository.findTransactionByDate(date);
		if(!list.isEmpty())
			return list;
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transaction With the date " + date + " does not exits");
		}
		
	}
	
public List<Transaction> getTransactionsByDateAndType(LocalDate date, String type) {
		
		List<Transaction> list=transactionRepository.findByTransactionTypeAndDate(type, date);
		
		if(!list.isEmpty())
			return list;
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Transactions with the given DateAndType " + date + "-" + type + " Doesn't Exist! ");
		}
		

	}
			



	
	


		
		
	
}
