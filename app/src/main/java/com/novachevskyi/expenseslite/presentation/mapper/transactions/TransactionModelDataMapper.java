package com.novachevskyi.expenseslite.presentation.mapper.transactions;

import com.novachevskyi.expenseslite.data.models.transactions.TransactionEntity;
import com.novachevskyi.expenseslite.data.models.transactions.TransactionsEntity;
import com.novachevskyi.expenseslite.presentation.model.transactions.Category;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionModel;
import com.novachevskyi.expenseslite.presentation.model.transactions.TransactionType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.joda.time.LocalDate;

public class TransactionModelDataMapper {

  public TransactionModel transform(TransactionEntity transactionEntity) {
    TransactionModel transactionModel = new TransactionModel();

    transactionModel.setTransactionId(transactionEntity.objectId);
    transactionModel.setAccountId(transactionEntity.accountId);
    transactionModel.setUserId(transactionEntity.userId);
    transactionModel.setType(TransactionType.getTransactionType(transactionEntity.type));
    transactionModel.setCategory(Category.getCategory(transactionEntity.category));
    if (transactionEntity.amount != null) {
      transactionModel.setAmount(transactionEntity.amount);
    }
    transactionModel.setNotes(transactionEntity.notes);
    transactionModel.setPayee(transactionEntity.payee);
    if (transactionEntity.paymentDate != null) {
      transactionModel.setPaymentDate(new LocalDate(transactionEntity.paymentDate));
    }

    return transactionModel;
  }

  private Collection<TransactionModel> transform(
      Collection<TransactionEntity> transactionsCollection) {
    Collection<TransactionModel> transactionModelCollection;

    if (transactionsCollection != null && !transactionsCollection.isEmpty()) {
      transactionModelCollection = new ArrayList<>();
      for (TransactionEntity transactionEntity : transactionsCollection) {
        transactionModelCollection.add(transform(transactionEntity));
      }
    } else {
      transactionModelCollection = Collections.emptyList();
    }

    return transactionModelCollection;
  }

  public Collection<TransactionModel> transform(TransactionsEntity transactionsEntity) {
    Collection<TransactionModel> transactionModelCollection = null;

    if (transactionsEntity != null) {
      TransactionEntity[] transactionsEntities = transactionsEntity.results;

      if (transactionsEntities != null) {
        transactionModelCollection = transform(Arrays.asList(transactionsEntities));
      }
    }

    if (transactionModelCollection == null) {
      transactionModelCollection = Collections.emptyList();
    }

    return transactionModelCollection;
  }
}
