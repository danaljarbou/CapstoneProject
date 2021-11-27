package com.saib.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.saib.config.ApiSuccessPayload;
import com.saib.models.Transaction;
import com.saib.services.TransactionService;
import com.saib.util.Results;

@RestController
public class TransactionController {
	
	/*
	 *  GET - /transaction - Get me all details 
	 *  GET - /transaction/id - Get me details for a single transaction
	 *  GET - /transaction/type - Get all transaction with a given type
	 *  POST - /transaction - Creating a new transaction 
	 *  PUT - /transaction/id - Updating an existing transaction 
	 *  DELETE -/transaction/id - for deleting an transaction from db
	 *  
	 *  
	 */
	
	@Autowired
	TransactionService transactionService;
	
	@GetMapping("/transaction")
	public ResponseEntity<ApiSuccessPayload> getAllTransactions()
	{
		List<Transaction> list=transactionService.getAllTransactions();
		
		ApiSuccessPayload payload=ApiSuccessPayload.build(list, "Transactions Fetched", HttpStatus.OK);
		ResponseEntity<ApiSuccessPayload> response=new ResponseEntity<ApiSuccessPayload>(payload,HttpStatus.OK);
		
		return response;
		
	}
	
	@GetMapping("/transaction/{transactionId}")
	public ResponseEntity<ApiSuccessPayload> getTransactionByTransactionNumber(@PathVariable long transactionId)
	{
		Transaction transaction=transactionService.getTransactionByTransactionNumber(transactionId);
		
		ApiSuccessPayload payload=ApiSuccessPayload.build(transaction, "Success",HttpStatus.OK);
		ResponseEntity<ApiSuccessPayload> response=new ResponseEntity<ApiSuccessPayload>(payload,HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/transaction/type/{type}")
	public ResponseEntity<ApiSuccessPayload> getTransactionByType(@PathVariable String type)
	{
		List<Transaction> list = transactionService.getTransactionsByType(type);
		
		ApiSuccessPayload payload=ApiSuccessPayload.build(list, "Success",HttpStatus.OK);
		ResponseEntity<ApiSuccessPayload> response=new ResponseEntity<ApiSuccessPayload>(payload,HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/transaction")
	public ResponseEntity<ApiSuccessPayload> addTransaction(@RequestBody Transaction transaction)
	{
		ResponseEntity<ApiSuccessPayload> response=null;
		System.out.println(transaction);
		String result=transactionService.addTransaction(transaction);
		if(result.equalsIgnoreCase(Results.SUCCESS))
		{
			ApiSuccessPayload payload=ApiSuccessPayload.build(result, "Transaction created successfully", HttpStatus.CREATED);
			response=new ResponseEntity<ApiSuccessPayload>(payload,HttpStatus.CREATED);
			
		}
		return response;
		
	}
	

	@PutMapping("/transaction/{transactionId}")
	public ResponseEntity<ApiSuccessPayload> updateTransaction(@RequestBody Transaction transaction, @PathVariable long transactionId)
	{
		String result=transactionService.updateTransaction(transaction, transactionId);
		ApiSuccessPayload payload=ApiSuccessPayload.build(result,result,HttpStatus.OK);
		ResponseEntity<ApiSuccessPayload> response=new ResponseEntity<ApiSuccessPayload>(payload, HttpStatus.OK);
		return response;
	}
	
	@DeleteMapping("/transaction/{transactionId}")
	public ResponseEntity<ApiSuccessPayload> deleteTransaction(@PathVariable long transactionId)
	{
		String result=transactionService.deleteTransaction(transactionId);
		ApiSuccessPayload payload=ApiSuccessPayload.build(result,result,HttpStatus.OK);
		ResponseEntity<ApiSuccessPayload> response=new ResponseEntity<ApiSuccessPayload>(payload, HttpStatus.OK);
		return response;
	}
	
	
	
}
