package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownTv;
    @BindView(R.id.also_known_as_label)
    TextView mAlsoKnownLabel;
    @BindView(R.id.place_of_origin_tv)
    TextView mOriginTv;
    @BindView(R.id.place_of_origin_label)
    TextView mOriginLabel;
    @BindView(R.id.description_tv)
    TextView mDescriptionTv;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientTv;
    @BindView(R.id.image_iv)
    ImageView mSandwichIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_detail );
        ButterKnife.bind( this );

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra( EXTRA_POSITION, DEFAULT_POSITION );
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray( R.array.sandwich_details );
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson( json );

        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI( sandwich );


        setTitle( sandwich.getMainName() );
    }

    private void closeOnError() {
        finish();
        Toast.makeText( this, R.string.detail_error_message, Toast.LENGTH_SHORT ).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (sandwich.getAlsoKnownAs() != null && sandwich.getAlsoKnownAs().size() > 0) {
            mAlsoKnownTv.setText( TextUtils.join( ", ", sandwich.getAlsoKnownAs() ) );
        } else {
            mAlsoKnownTv.setVisibility( View.GONE );
            mAlsoKnownLabel.setVisibility( View.GONE );
        }

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            mOriginTv.setVisibility( View.GONE );
            mOriginLabel.setVisibility( View.GONE );
        } else {
            mOriginTv.setText( sandwich.getPlaceOfOrigin() );
        }

        mDescriptionTv.setText( sandwich.getDescription() );

        if (sandwich.getIngredients() != null && sandwich.getIngredients().size() > 0) {
            for (int i = 0; i < sandwich.getIngredients().size(); i++) {
                mIngredientTv.setText( TextUtils.join( "\n \u2022 ", sandwich.getIngredients() ) );
            }
        }

        Picasso.with( this )
                .load( sandwich.getImage() )
                .placeholder( R.drawable.no_image_icon )
                .error( R.drawable.user_placeholder_error )
                .into( mSandwichIv );
    }
}
