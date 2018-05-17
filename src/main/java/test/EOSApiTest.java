package test;

import client.EosApiClientFactory;
import client.EosApiRestClient;
import client.crypto.ec.EosPrivateKey;
import client.crypto.ec.EosPublicKey;
import client.domain.common.transaction.PackedTransaction;
import client.domain.common.transaction.SignedPackedTransaction;
import client.domain.common.transaction.TransactionAction;
import client.domain.common.transaction.TransactionAuthorization;
import client.domain.response.chain.AbiJsonToBin;
import client.domain.response.chain.Block;
import client.domain.response.chain.ChainInfo;
import client.domain.response.chain.code.Action;
import client.domain.response.chain.transaction.PushedTransaction;
import client.model.chain.SignedTransaction;
import client.model.types.EosNewAccount;
import io.reactivex.Observable;

import java.util.*;

/**
 * Created by Administrator on 2018/5/15.
 */
public class EOSApiTest {


    public static void main(String[] args) {

        EosApiRestClient client = EosApiClientFactory.newInstance("http://192.168.22.202:8888").newRestClient();

        /* Create the json array of arguments */
        Map<String, String> params = new HashMap<>(4);
        params.put("from", "eosio.token");
        params.put("to", "eosio");
        params.put("quantity", "50.0000 EOS");
        params.put("memo", "My First Transaction");
        AbiJsonToBin data = client.abiJsonToBin("eosio.token", "transfer", params);

        /* Get the head block */
        ChainInfo chainInfo = client.getChainInfo();
        Block block = client.getBlock(chainInfo.getHeadBlockId());

        /* Create Transaction Action Authorization */
        TransactionAuthorization transactionAuthorization = new TransactionAuthorization();
        transactionAuthorization.setActor("eosio.token");
        transactionAuthorization.setPermission("active");


        /* Create Transaction Action */
        TransactionAction transactionAction = new TransactionAction();
        transactionAction.setAccount("eosio.token");
        transactionAction.setName("transfer");
        transactionAction.setData(data.getBinargs());
        transactionAction.setAuthorization(Collections.singletonList(transactionAuthorization));

        /* Create a transaction */
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix().toString());
        packedTransaction.setRefBlockNum(block.getBlockNum().toString());
        packedTransaction.setExpiration(block.getTimeStamp());
        packedTransaction.setRegion("0");
        packedTransaction.setMax_net_usage_words("0");
        packedTransaction.setContextFreeData(Collections.emptyList());
        packedTransaction.setContextFreeActions(Collections.emptyList());
        packedTransaction.setActions(Collections.singletonList(transactionAction));

        /* Sign the Transaction */
        List<String> publicKeys = Arrays.asList("EOS6VvToov21Xyd9jGESVm3JXrMXPs9q861Wi3g2yrWj4CqzPXoBa");
        SignedPackedTransaction signedPackedTransaction = client.signTransaction(packedTransaction, publicKeys, "0000000000000000000000000000000000000000000000000000000000000000");

        /* Push the transaction */
        PushedTransaction none = client.pushTransaction("none", signedPackedTransaction);
        System.out.println(none);


    }

}