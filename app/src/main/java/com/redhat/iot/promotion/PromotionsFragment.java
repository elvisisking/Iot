package com.redhat.iot.promotion;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.redhat.iot.DataProvider;
import com.redhat.iot.R;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Promotion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A screen showing the current promotions.
 */
public class PromotionsFragment extends Fragment implements View.OnClickListener {

    private Activity activity;
    private PromotionAdapter adapter;
    private final Collection< CheckBox > chkDepts = new ArrayList<>();

    public PromotionsFragment() {
        // nothing to do
    }

    @Override
    public void onActivityCreated( final Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        // get all current promotions
        final Promotion[] promotions = DataProvider.get().getPromotions();
        this.adapter = new PromotionAdapter( this.activity, promotions );

        final RecyclerView promotionssView = ( RecyclerView )getActivity().findViewById( R.id.gridDeals );
        promotionssView.setAdapter( this.adapter );
        promotionssView.setLayoutManager( new GridLayoutManager( this.activity, 1 ) );
    }

    public void onClick( final View view ) {
        final List< Long > selected = new ArrayList<>( this.chkDepts.size() );

        for ( final CheckBox chk : this.chkDepts ) {
            if ( chk.isChecked() ) {
                selected.add( ( long )chk.getTag() );
            }
        }

        // reload requested promotions
        this.adapter.setFilter( selected.toArray( new Long[ selected.size() ] ) );
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup parent,
                              final Bundle savedInstanceState ) {
        this.activity = getActivity();
        final View promotionsView = inflater.inflate( R.layout.promotions, parent, false );

        // create department checkboxes
        final Department[] departments = DataProvider.get().getDepartments();
        final TableLayout table = ( TableLayout )promotionsView.findViewById( R.id.promoDepartments );
        TableRow row = null;
        int i = 0;

        for ( final Department dept : departments ) {
            if ( i % 3 == 0 ) {
                row = new TableRow( activity );
                final TableRow.LayoutParams params = new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                                                                                TableRow.LayoutParams.WRAP_CONTENT );
                row.setLayoutParams( params );
                table.addView( row );
            }

            {// dept checkbox
                final CheckBox chk = new CheckBox( activity );
                chk.setText( dept.getName() );

                final ViewGroup.LayoutParams params = new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                                                                                 TableRow.LayoutParams.WRAP_CONTENT );
                chk.setLayoutParams( params );
                chk.setPadding( 10, 10, 10, 10 );
                chk.setText( dept.getName() );
                chk.setTextColor( getResources().getColor( R.color.textColorPrimary, null ) );
                chk.setTextSize( 20.0f );
//                chk.setBackgroundColor( DataProvider.get().getDepartmentColor( dept ) );
                chk.setTag( dept.getId() );
                chk.setChecked( true );
                chk.setOnClickListener( this );
                row.addView( chk );

                this.chkDepts.add( chk );
            }

            ++i;
        }

        return promotionsView;
    }

}
