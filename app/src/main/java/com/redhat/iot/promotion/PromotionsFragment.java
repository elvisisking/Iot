package com.redhat.iot.promotion;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

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
    private GridView grid;
    private final Collection< CheckBox > chkDepts = new ArrayList<>();

    public PromotionsFragment() {
        // nothing to do
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,
                              final ViewGroup parent,
                              final Bundle savedInstanceState ) {
        this.activity = getActivity();
        final View view = inflater.inflate( R.layout.promotions, parent, false );

        this.grid = ( GridView )view.findViewById( R.id.gridDeals );

        final Promotion[] promotions = DataProvider.get().getPromotions();
        this.adapter = new PromotionAdapter( this.activity, promotions );
        grid.setAdapter( adapter );
        grid.setOnItemClickListener( new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick( final AdapterView< ? > parent,
                                     final View view,
                                     final int position,
                                     final long id ) {
                Toast.makeText( getActivity(), "You Clicked at " + promotions[ +position ], Toast.LENGTH_SHORT ).show();
            }
        } );

        // create department checkboxes
        final Department[] departments = DataProvider.get().getDepartmentsFromJson();
        final TableLayout table = ( TableLayout )view.findViewById( R.id.promoDepartments );
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
                chk.setText( dept.getName() );
                chk.setTag( dept.getId() );
                chk.setChecked( true );
                chk.setOnClickListener( this );
                row.addView( chk );

                this.chkDepts.add( chk );
            }

            ++i;
        }
        return view;
    }

    public void onClick( final View view ) {
        final List< Long > selected = new ArrayList<>( this.chkDepts.size() );

        for ( final CheckBox chk : this.chkDepts ) {
            if ( chk.isChecked() ) {
                selected.add( ( long )chk.getTag() );
            }
        }

        this.adapter.notifyDataSetInvalidated();
        this.adapter = new PromotionAdapter( this.activity,
                                             DataProvider.get().getPromotions( selected.toArray( new Long[ selected.size() ] ) ) );
        this.grid.setAdapter( this.adapter );
        this.adapter.notifyDataSetChanged();
    }

}
