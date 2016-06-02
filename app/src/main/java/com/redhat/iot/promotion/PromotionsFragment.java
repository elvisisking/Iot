package com.redhat.iot.promotion;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.redhat.iot.DataProvider;
import com.redhat.iot.IotApp;
import com.redhat.iot.IotConstants;
import com.redhat.iot.R;
import com.redhat.iot.domain.Department;
import com.redhat.iot.domain.Promotion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        this.adapter = new PromotionAdapter( this.activity, null );

        Long[] filter;

        // get promo dept ID preference
        final Set< String > deptIdsPref = IotApp.getPrefs().getStringSet( IotConstants.Prefs.PROMO_DEPT_IDS, null );

        if ( ( deptIdsPref == null ) || deptIdsPref.isEmpty() ) {
            final Department[] all = DataProvider.get().getDepartments();
            filter = new Long[ all.length ];
            int i = 0;

            for ( final Department dept : all ) {
                filter[ i++ ] = dept.getId();
            }
        } else {
            filter = new Long[ deptIdsPref.size() ];
            int i = 0;

            for ( final String deptId : deptIdsPref ) {
                filter[ i++ ] = Long.parseLong( deptId );
            }
        }

        // set checkbox state based on filter
        for ( final CheckBox chk : this.chkDepts ) {
            boolean check = false;

            for ( final Long deptId : filter ) {
                if ( chk.getTag().equals( deptId ) ) {
                    check = true;
                    break;
                }
            }

            chk.setChecked( check );
        }

        this.adapter.setFilter( filter );

        final RecyclerView promotionssView = ( RecyclerView )getActivity().findViewById( R.id.gridDeals );
        promotionssView.setAdapter( this.adapter );
        promotionssView.setLayoutManager( new GridLayoutManager( this.activity, 1 ) );
    }

    public void onClick( final View view ) {
        final List< Long > selected = new ArrayList<>( this.chkDepts.size() );
        final Set< String > prefValue = new HashSet<>();

        for ( final CheckBox chk : this.chkDepts ) {
            if ( chk.isChecked() ) {
                selected.add( ( long )chk.getTag() );
                prefValue.add( chk.getTag().toString() );
            }
        }

        { // save selected dept IDs in preference
            final SharedPreferences.Editor editor = IotApp.getPrefs().edit();
            editor.putStringSet( IotConstants.Prefs.PROMO_DEPT_IDS, prefValue );
            editor.apply();
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
        int childIndex = 0;

        for ( final Department dept : departments ) {
            if ( i % 3 == 0 ) {
                childIndex = 0;
                row = new TableRow( activity );
                final TableRow.LayoutParams params = new TableRow.LayoutParams( TableRow.LayoutParams.WRAP_CONTENT,
                                                                                TableRow.LayoutParams.WRAP_CONTENT );
                row.setLayoutParams( params );
                table.addView( row );
            }

            { // create dept checkbox
                inflater.inflate( R.layout.promo_dept_chk, row, true ); // adds to row
                final CheckBox chk = ( CheckBox )row.getChildAt( childIndex++ );
                chk.setText( dept.getName() );

                { // color background of button
                    final int[][] states = {
                        { android.R.attr.state_enabled },
                    };

                    final int[] colors = {
                        DataProvider.get().getDepartmentColor( dept ),
                    };

                    ColorStateList colorStateList = new ColorStateList( states, colors );
                    chk.setButtonTintList( colorStateList );
                    chk.setButtonTintMode( PorterDuff.Mode.DST_OVER );
//                    chk.setButtonTintMode( PorterDuff.Mode.DST );
                }

                chk.setTag( dept.getId() );
                chk.setOnClickListener( this );

                this.chkDepts.add( chk );
            }

            ++i;
        }

        return promotionsView;
    }

}
