package com.example.kenny.codeblocksbankingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
<<<<<<< HEAD
import android.widget.TextView;

import com.example.kenny.codeblocksbankingapp.BankActivity;
=======
import android.widget.ListView;

import java.util.ArrayList;
>>>>>>> 8c4977b2d894e944babdc16049d4cc865d3cdd5f

/**
 * Fragment for display the accounts summary page
 */

public class BankAccountsSummaryFragment extends Fragment{

    //The fragment OnAccountImageViewListener,
    //Refer to the inner interface class on this listener

    OnAccountsImageViewButtonClickListener onAccountsImageViewButtonClickListener ;

    private View fragmentView;

    /*These are the clickable imageview that display the
    * various accounts. One for eac account type*/
    private ImageView savings_imageview_button ;
    private ImageView checkings_imageview_button;
    private ImageView investments_imageview_button;

<<<<<<< HEAD
    //Displays for the amounts in each account
    //TODO: add investment amount and account number to database and update the view
    private TextView txt_checkingAmount;
    private TextView txt_savingsAmount;
    private TextView txt_InvestmentAmount;

    //Displays for the account number
    private TextView txt_savingsAccountDescription;
=======


>>>>>>> 8c4977b2d894e944babdc16049d4cc865d3cdd5f

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
      fragmentView = inflater.inflate(R.layout.fragment_bank_accounts_summary_page, container, false);
        // Get the values like this
        //amount.setText(this.getArguments().getInt("SOME STRING KEY FOR THIS INFO"))
        setupClickableImageViews();
        setUpTextViews();
        return fragmentView;

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

    /*This interface implemented by BankActivity help this  fragment
    * 1) Is this fragment's way of telling it's containing activity about
    * Account ImageView click events
    * 2) it uses the id of the imageView to let the activity no what
    * Account ImageView was clicked*/
    public interface OnAccountsImageViewButtonClickListener{
        public void onAccountsImageViewButton(int imageViewButtonID);
    }

    public void setUpTextViews(){

        String[] loadCustomerInfo;
        BankActivity masterBank = new BankActivity();

        txt_checkingAmount = fragmentView.findViewById(R.id.txt_checkingAmountDisplay);
        txt_savingsAmount = fragmentView.findViewById(R.id.txt_savingsAmountDisplay);
        txt_InvestmentAmount = fragmentView.findViewById(R.id.txt_investmentsAmountDisplay);

        txt_savingsAccountDescription = fragmentView.findViewById(R.id.txt_savingsAccountDescription);

        loadCustomerInfo = masterBank.currentCustomerInfo(masterBank.currentCustomerAccessCardNo);

        txt_savingsAmount.setText(loadCustomerInfo[4]);
        txt_checkingAmount.setText(loadCustomerInfo[2]);

        txt_savingsAccountDescription.setText(loadCustomerInfo[3]);



    }
    /*This methods helps sets uo the image view buttons.
    * and attaches a listener which invokes the onAccountImageViewButton callback method*/
    public void setupClickableImageViews(){
       // savings_imageview_button = getActivity().findViewById(R.id.savings_imageview_button);
        savings_imageview_button = fragmentView.findViewById(R.id.btn_savingsImage);
        checkings_imageview_button = fragmentView.findViewById(R.id.btn_checkingImage);
        investments_imageview_button = fragmentView.findViewById(R.id.btn_investmentsImage);

        savings_imageview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    onAccountsImageViewButtonClickListener.
                            onAccountsImageViewButton(R.id.btn_savingsImage);
            }
        });

        checkings_imageview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAccountsImageViewButtonClickListener.
                        onAccountsImageViewButton(R.id.checkings_imageview_button);
            }
        });

        investments_imageview_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAccountsImageViewButtonClickListener.
                        onAccountsImageViewButton(R.id.investments_imageview_button);
            }
        });
    }
    
}
