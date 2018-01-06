package com.example.kenny.codeblocksbankingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.TextView;


/**
 * Fragment for displaying the account summary page
 */

public class BankAccountsSummaryFragment extends Fragment{

    //The fragment OnAccountImageViewListener,
    //Refer to the inner interface class on this listener

    OnAccountsImageViewButtonClickListener onAccountsImageViewButtonClickListener ;

    private View fragmentView;

    /*These are the clickable imageview that display the
    * various accounts. One for eac account type*/
    public ImageView btnSavingsImage;
    public ImageView btnCheckingImage;
    public ImageView btnInvestmentImage;

    //Displays for the amounts in each account
    //TODO: add investment amount and account number to database and update the view
    public TextView txt_checkingAmount;
    public TextView txt_savingsAmount;
    public TextView txt_InvestmentAmount;

    //Bank activity reference
    public BankActivity masterBank;

    //info array stores current user information
    public String[] info;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
      fragmentView = inflater.inflate(R.layout.fragment_bank_accounts_summary_page, container, false);

        masterBank = new BankActivity();
        //get the bundle passed in from bank to populate the info array
        info = getArguments().getStringArray("CURRENT CUSTOMER INFO ARRAY");

        //set up views
        setupClickableImageViews();
        setUpTextViews();

        return fragmentView;

    }


    /*This interface implemented by BankActivity help this  fragment
    * 1) Is this fragment's way of telling it's containing activity about
    * Account ImageView click events
    * 2) it uses the id of the imageView to let the activity no what
    * Account ImageView was clicked*/
    public interface OnAccountsImageViewButtonClickListener{
        void onAccountsImageViewButton(int imageViewButtonID);
    }

    //populate views in this fragment
    public void setUpTextViews(){
        txt_checkingAmount = fragmentView.findViewById(R.id.txt_checkingAmountDisplay);
        txt_savingsAmount = fragmentView.findViewById(R.id.txt_savingsAmountDisplay);
        txt_InvestmentAmount = fragmentView.findViewById(R.id.txt_investmentsAmountDisplay);

        txt_savingsAmount.setText(info[4]);
        txt_checkingAmount.setText(info[2]);

    }

    /*This methods helps sets up the image view buttons.
    * and attaches a listener which invokes the onAccountImageViewButton callback method*/
    public void setupClickableImageViews(){
        btnSavingsImage = fragmentView.findViewById(R.id.btn_savingsImage);
        btnCheckingImage = fragmentView.findViewById(R.id.btn_checkingImage);
        btnInvestmentImage = fragmentView.findViewById(R.id.btn_investmentsImage);

        btnSavingsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onAccountsImageViewButtonClickListener.
                            onAccountsImageViewButton(R.id.btn_savingsImage);
            }
        });

        btnCheckingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAccountsImageViewButtonClickListener.
                        onAccountsImageViewButton(R.id.btn_checkingImage);
            }
        });

        btnInvestmentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAccountsImageViewButtonClickListener.
                        onAccountsImageViewButton(R.id.btn_investmentsImage);
            }
        });
    }

    /*This overrider of fragment's onAttach
    * 1) Is to the activity that this fragment is attached to implements the
    * OnAccountsImageViewButtonClickListener interfer*/
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try {
            onAccountsImageViewButtonClickListener = (OnAccountsImageViewButtonClickListener) context;
        }catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnAccountsImageViewButtonClickListener");
        }

    }
    
}
