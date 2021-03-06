package com.fafabtc.app.vm;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.fafabtc.app.utils.RxUtils;
import com.fafabtc.data.data.repo.BlockchainAssetsRepo;
import com.fafabtc.data.model.entity.exchange.Portfolio;
import com.fafabtc.data.model.entity.exchange.BlockchainAssets;
import com.fafabtc.data.model.entity.exchange.Exchange;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by jastrelax on 2018/1/14.
 */

public class BlockchainAssetsViewModel extends ViewModel{

    private Portfolio portfolio;
    private Exchange exchange;

    MutableLiveData<List<BlockchainAssets>> blockchainAssetsData = new MutableLiveData<>();

    @Inject
    BlockchainAssetsRepo blockchainAssetsRepo;

    @Inject
    public BlockchainAssetsViewModel() {
    }

    public void loadBlockchainAssets() {
        blockchainAssetsRepo.getBaseBlockchainAssets(portfolio.getUuid(), exchange.getName())
                .compose(RxUtils.<List<BlockchainAssets>>singleAsyncIO())
                .subscribe(new SingleObserver<List<BlockchainAssets>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<BlockchainAssets> blockchainAssets) {
                        blockchainAssetsData.setValue(blockchainAssets);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }
                });
    }

    public MutableLiveData<List<BlockchainAssets>> getBlockchainAssetsData() {
        return blockchainAssetsData;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }
}
