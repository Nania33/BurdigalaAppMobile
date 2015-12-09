package com.enseirb.pfa.bastats.tournoi;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.enseirb.pfa.bastats.R;
import com.enseirb.pfa.bastats.data.DAO.DBEquipeDAO;
import com.enseirb.pfa.bastats.data.DAO.DBMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTournoiDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTournoiEquipeDAO;
import com.enseirb.pfa.bastats.data.model.Equipe;
import com.enseirb.pfa.bastats.data.model.Tournoi;
import com.enseirb.pfa.bastats.lancement.AjouterEquipe;
import com.enseirb.pfa.bastats.match.Timer;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SelectionEquipeTournoiActivity extends ActionBarActivity {

    private static final String TAG = "SelectionEquipeTournoi";

    private static final int CODE_AJOUT_EQUIPE = 1;
    private static final int REQUEST_IMPORT = 22;

    private Context mCtx;
    private int tournoiId;

    private Button buttonSelectionEquipe;
    private Button buttonValider;
    private Button buttonAddTeam;

    private ArrayList<HashMap<String,String>> mListeEquipesSelection;
    private ArrayList<HashMap<String,String>> mListeEquipesDb;
    private ListView mListeView;
    private SimpleAdapter mAdapter;

    private boolean[] selectVal;
    private CharSequence[] charSequenceItems;
    private List<Integer> toRemove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_equipe_tournoi);

        mCtx = this;
        tournoiId = getIntent().getIntExtra(KeyTable.ARG_ID_TOURNOI, -1);
        Log.d(TAG, "Récupération de l'idTournoi: " + tournoiId);

        buttonValider = (Button) findViewById(R.id.button_valider);
        buttonAddTeam = (Button) findViewById(R.id.button_add_team);

        buttonAddTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mCtx, AjouterEquipe.class);
                startActivityForResult(intent, CODE_AJOUT_EQUIPE);
            }
        });

        mListeView = (ListView) findViewById(R.id.liste_equipes_tournoi);
        mListeEquipesSelection = new ArrayList<>();
        mListeEquipesDb = new ArrayList<>();

        getTeamsFromDb();

        mAdapter = new SimpleAdapter(this, mListeEquipesSelection,R.layout.row_equipe_poule,
                new String[]{"nom","bouton"},
                new int[]{R.id.nom_equipe, R.id.supprimer_equipe}) {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                final int index = position;
                ImageButton buttonDelete = (ImageButton) view.findViewById(R.id.supprimer_equipe);
                buttonDelete.setImageDrawable(mCtx.getResources().getDrawable(R.drawable.ic_action_delete));
                buttonDelete.setBackground(mCtx.getResources().getDrawable(R.drawable.button_delete));
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String,String> map = new HashMap<String,String>();
                        map.put("nom", mListeEquipesSelection.get(index).get("nom"));
                        map.put("id", mListeEquipesSelection.get(index).get("id"));
                        mListeEquipesDb.add(map);
                        mListeEquipesSelection.remove(index);
                        setUpSelection();
                        mAdapter.notifyDataSetChanged();
                    }
                });
                return view;
            }
        };

        buttonSelectionEquipe = (Button) findViewById(R.id.selection_equipes);

        buttonSelectionEquipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Sélection des équipes");
                builder.setMultiChoiceItems(charSequenceItems, selectVal,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which,
                                                boolean isChecked) {
                                selectVal[which] = isChecked;
                            }
                        });
                builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG,selectVal.toString());
                        for (int i = 0; i < mListeEquipesDb.size(); i++) {
                            if (selectVal[i]) {
                                Log.d(TAG,"valeur de i:"+i);
                                HashMap<String,String> map = new HashMap<String,String>();
                                map.put("nom", mListeEquipesDb.get(i).get("nom"));
                                map.put("id", mListeEquipesDb.get(i).get("id"));
                                Log.d(TAG, "Selection équipe "+map.get("nom")+" avec l'id "+map.get("id"));
                                mListeEquipesSelection.add(map);
                                toRemove.add(i);
                            }
                        }
                        Collections.sort(toRemove);
                        Collections.reverse(toRemove);
                        // On les ranges dans l'ordre décroissant pour supprimer de droite à gauche
                        Log.d(TAG,toRemove.toString());
                        for(int i : toRemove){
                            mListeEquipesDb.remove(i);
                            Log.d(TAG,"remove élément "+i);
                        }
                        setUpSelection();
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.dialog_cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        buttonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBTournoiDAO tableTournoi = new DBTournoiDAO(mCtx);
                Tournoi tournoi = new Tournoi(tableTournoi.getWithId(tournoiId));
                tournoi.setNbEquipeMax(mListeEquipesSelection.size());
                tableTournoi.update(tournoiId, tournoi);
                Log.d(TAG," Nombre d'équipes du tournoi:"+mListeEquipesSelection.size());
                DBTournoiEquipeDAO tableTournoiEquipe = new DBTournoiEquipeDAO(mCtx);
                for (HashMap<String,String> team : mListeEquipesSelection){
                    int teamId = Integer.parseInt(team.get("id"));
                    tableTournoiEquipe.insert(tournoiId, teamId);
                    Log.d(TAG, "L'équipe " + teamId + " participe au tournoi "+tournoiId);
                }
                Intent intent = new Intent(mCtx, NavigationDrawerTournoi.class);
                intent.putExtra(KeyTable.ARG_ID_TOURNOI, tournoiId);
                startActivity(intent);
                finish();
            }
        });

        mListeView.setAdapter(mAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_selection_equipe_tournoi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getTeamsFromDb(){
        DBEquipeDAO tableEquipe = new DBEquipeDAO(mCtx);
        mListeEquipesDb.clear();
        List<Equipe> tmp = new ArrayList<>();
        if (tableEquipe.getAll() != null)
            tmp.addAll(tableEquipe.getAll());
        Log.d(TAG,"Copie des équipes de la bdd");

        for (Equipe team : tmp){
            HashMap<String,String> map = new HashMap<>();
            map.put("nom",team.getNom());
            map.put("id",String.valueOf(team.getId()));
            Log.d(TAG, "Copie équipe "+ team.getNom()+ "avec l'id "+team.getId());
            mListeEquipesDb.add(map);
        }

        setUpSelection();
    }

    public void setUpSelection(){
        int size = mListeEquipesDb.size();
        Log.d(TAG,"Nombre d'équipes: "+size);
        //Log.d(TAG,mListeEquipesDb.toString());
        selectVal = new boolean[size];
        charSequenceItems = new CharSequence[size];
        toRemove = new ArrayList<>();
        for (int i=0; i < size; i++){
            selectVal[i] = false;
            charSequenceItems[i] = mListeEquipesDb.get(i).get("nom");
            //Log.d(TAG, "Ajout nom equipe"+mListeEquipesDb.get(i).get("nom"));
        }
    }

    public void delete(int i){
        mListeEquipesSelection.remove(i);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        DBTournoiDAO tableTournoi = new DBTournoiDAO(mCtx);
        int id = tableTournoi.getLast().getId();
        tableTournoi.removeWithId(id);
        finish();
    }

    private String showDirectoryExplorer(String road){
        final Dialog d = new Dialog(this);

        final String[] path = new String[]{null};
        d.setTitle("Importation de données");
        d.setContentView(R.layout.dialog_explorer);
        Button positiveButton = (Button) d.findViewById(R.id.exp_positiveButton);
        Button negativeButton = (Button) d.findViewById(R.id.exp_negativeButton);

        /**
         * Représente le texte qui s'affiche quand la liste est vide
         */
        TextView mEmpty = null;
        /**
         * La liste qui contient nos fichiers et répertoires
         */
        ListView mList = null;
        /**
         * Notre Adapter personnalisé qui lie les fichiers à la liste
         */
        FileAdapter myAdapter = null;


        /**
         * Représente le répertoire actuel
         */
        File mCurrentFile = null;

        // On récupère la ListView de notre activité
        mList = (ListView) d.findViewById(R.id.explo_list);

        File my_road = new File(road);
        // On vérifie que le répertoire externe est bien accessible
        if(false){//!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState(my_road))) {
            // S'il ne l'est pas, on affiche un message
            mEmpty = (TextView) mList.getEmptyView();
            mEmpty.setText("Vous ne pouvez pas accéder aux fichiers");
        } else {
            // S'il l'est...
            // On déclare qu'on veut un menu contextuel sur les éléments de la liste
            registerForContextMenu(mList);

            // On récupère la racine de la carte SD pour qu'elle soit
            mCurrentFile = Environment.getExternalStorageDirectory();

            // On récupère la liste des fichiers dans le répertoire actuel
            File[] fichiers = my_road.listFiles();//mCurrentFile.listFiles();



            // On transforme le tableau en une structure de données de taille variable
            ArrayList<File> liste = new ArrayList<File>();
            for(File f : fichiers)
                liste.add(f);




            myAdapter = new FileAdapter(this, android.R.layout.simple_list_item_1, liste);

            // On ajoute l'adaptateur à la liste
            mList.setAdapter(myAdapter);


            // On trie la liste
            myAdapter.sort();

            // On ajoute un Listener sur les items de la liste
            final FileAdapter finalMyAdapter = myAdapter;
            mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                // Que se passe-il en cas de cas de clic sur un élément de la liste ?
                public void onItemClick(AdapterView<?> adapter, View view,
                                        int position, long id) {
                    File fichier = finalMyAdapter.getItem(position);
                    // Si le fichier est un répertoire...
                    if(fichier.isDirectory())
                        // On change de répertoire courant
                        Toast.makeText(getApplicationContext(), "Ceci est un répertoire. Veuillez selectionner un fichier", Toast.LENGTH_SHORT);
                    else {
                        // Sinon on lance l'irzm
                        path[0] = fichier.getAbsolutePath();
                        Log.d("FICHIER!!!!",path[0]);
                       //setPath(fichier,path[0]);

                        view.setBackgroundColor(getResources().getColor(R.color.grey_stats));

                    }
                }
            });
        }

        // Buttons
        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if (path.equals(null)) {
                    //Toast.makeText(getApplicationContext(), "Selectionnez un fichier", Toast.LENGTH_SHORT);
                } else {

                    DBMatchDAO dbm=new DBMatchDAO(mCtx);
                    DBEquipeDAO dbe=new DBEquipeDAO(mCtx);
                    String file =dbm.lireFichier(path[0]);
                    Log.d("FICHIER!!!!!",file);
                    dbe.importEquipes(file);
                    Toast.makeText(mCtx, "Import réalisé avec succès", Toast.LENGTH_LONG).show();
                    getTeamsFromDb();
                    d.dismiss();
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
        return path[0];
    }

    /**
     * Utilisé pour visualiser un fichier
     * @param pFile le fichier à visualiser
     */
    private void setPath(File pFile, String path) {
        // On créé un Intent
        path ="hello";//pFile.getAbsolutePath();
    }

    /**
     * L'adaptateur spécifique à nos fichiers
     */

    private class FileAdapter extends ArrayAdapter<File> {
        /**
         * Permet de comparer deux fichiers
         *
         */
        private class FileComparator implements Comparator<File> {

            public int compare(File lhs, File rhs) {
                // si lhs est un répertoire et pas l'autre, il est plus petit
                if(lhs.isDirectory() && rhs.isFile())
                    return -1;
                // dans le cas inverse, il est plus grand
                if(lhs.isFile() && rhs.isDirectory())
                    return 1;

                //Enfin on ordonne en fonction de l'ordre alphabétique sans tenir compte de la casse
                return lhs.getName().compareToIgnoreCase(rhs.getName());
            }

        }

        public FileAdapter(Context context, int textViewResourceId,
                           List<File> objects) {
            super(context, textViewResourceId, objects);
            mInflater = LayoutInflater.from(context);
        }

        private LayoutInflater mInflater = null;

        /**
         * Construit la vue en fonction de l'item
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView vue = null;

            if(convertView != null)
                vue = (TextView) convertView;
            else
                vue = (TextView) mInflater.inflate(android.R.layout.simple_list_item_1, null);
            File item = getItem(position);
            //Si c'est un répertoire, on choisit la couleur dans les préférences
            vue.setText(item.getName());

            return vue;
        }

        /**
         * Pour trier rapidement les éléments de l'adaptateur
         */
        public void sort () {
            super.sort(new FileComparator());
        }
    }

    public void importEquipe(View v){
        // TODO Lancer activité d'import
        //DBEquipeDAO dbTeam=new DBEquipeDAO(mCtx);
        //dbTeam.importEquipes();
        String importe = showDirectoryExplorer(Environment.getExternalStorageDirectory().getAbsolutePath() +
                File.separator + "Android"+File.separator+"data"+File.separator+"BastatsFiles"+File.separator);
        //Log.d("Importt", importe);
        // startActivityForResult(intent, REQUEST_IMPORT);

    }

    private void updateSelection(){
        DBEquipeDAO tableEquipe = new DBEquipeDAO(mCtx);
        if (tableEquipe.getLast() != null) {
            Equipe team = new Equipe(tableEquipe.getLast());
            HashMap<String, String> map = new HashMap<>();
            map.put("nom", team.getNom());
            map.put("id", String.valueOf(team.getId()));
            Log.d(TAG, "Copie équipe " + team.getNom() + "avec l'id " + team.getId());
            mListeEquipesDb.add(0, map);
            setUpSelection();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == CODE_AJOUT_EQUIPE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                updateSelection();
            }
        }
        if (requestCode == REQUEST_IMPORT) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                updateSelection();
            }
        }
    }
}
