package com.example.savor.plan.presenter;

import com.example.savor.model.MealsRepositoryImp;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragmentPresenterImp implements PlanFragmentPresenter{
    MealsRepositoryImp mealsRepositoryImp;
    PlanFragmentContract  planFragmentContract;
    private static final String TAG = "PlanFragmentPresenterIm";
    public PlanFragmentPresenterImp(MealsRepositoryImp mealsRepositoryImp, PlanFragmentContract planFragmentContract) {
        this.mealsRepositoryImp = mealsRepositoryImp;
        this.planFragmentContract = planFragmentContract;
    }


    @Override
    public void showPlan() {
        Disposable subscribe = mealsRepositoryImp.getPlaneMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mealsItemList -> {
                            planFragmentContract.showPlanMeals(mealsItemList);
                        }, throwable -> {
                            planFragmentContract.showError("NO Plan");
                        }
                );
    }

    @Override
    public void deleteFromPlan(String id) {
        mealsRepositoryImp.deleteMealFromPlan(id)
                .andThen(mealsRepositoryImp.deleteUnUsedMeals())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    planFragmentContract.showSuccessMsg("Deleted");
                }, throwable -> {
                    planFragmentContract.showError("Not Deleted");
                });

    }
}
