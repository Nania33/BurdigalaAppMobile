package com.enseirb.pfa.bastats.stat;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.enseirb.pfa.bastats.R;
import com.enseirb.pfa.bastats.data.DAO.DBEquipeDAO;
import com.enseirb.pfa.bastats.data.DAO.DBFormationDAO;
import com.enseirb.pfa.bastats.data.DAO.DBJoueurDAO;
import com.enseirb.pfa.bastats.data.DAO.DBMatchDAO;
import com.enseirb.pfa.bastats.data.DAO.DBStatDAO;
import com.enseirb.pfa.bastats.data.DAO.DBTempsDeJeuDAO;
import com.enseirb.pfa.bastats.data.DAO.action.DBActionDAO;
import com.enseirb.pfa.bastats.data.model.Joueur;
import com.enseirb.pfa.bastats.data.model.Match;
import com.enseirb.pfa.bastats.data.model.Stat;
import com.enseirb.pfa.bastats.data.model.TempsDeJeu;
import com.enseirb.pfa.bastats.data.model.action.Action;
import com.enseirb.pfa.bastats.pdf.PdfCreator;
import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OverviewMatchFragment extends Fragment {
    private static final String ARG_MATCH_ID = "matchId";
    private int matchId;

    private Context mCtx;

    public static OverviewMatchFragment newInstance(int matchId) {
        Log.d("HISTO", "call newInstance");
        OverviewMatchFragment fragment = new OverviewMatchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MATCH_ID, matchId);
        fragment.setArguments(args);
        return fragment;
    }

    public OverviewMatchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("OverviewMatch", "call onCreate");
        if (getArguments() != null) {
            matchId = getArguments().getInt(ARG_MATCH_ID);
        }
        mCtx = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vue_stats_match, container, false);

        DBMatchDAO tableMatch = new DBMatchDAO(mCtx);
        DBEquipeDAO tableEquipe = new DBEquipeDAO(mCtx);
        DBFormationDAO tableFormation = new DBFormationDAO(mCtx);

        Match match = tableMatch.getWithId(matchId);

        TextView libelle = (TextView) view.findViewById(R.id.textView_libelle_match);
        libelle.setText(match.getLibelle());

        TextView nomEquipeA = (TextView) view.findViewById(R.id.textView_equipeA);
        if (match.getFormationEquipeA() != -1)
        nomEquipeA.setText(tableEquipe.getWithId(
                tableFormation.getWithId(match.getFormationEquipeA()).getEquipeId()).getNom());

        TextView nomEquipeB = (TextView) view.findViewById(R.id.textView_equipeB);
        if (match.getFormationEquipeB() != -1)
            nomEquipeB.setText(tableEquipe.getWithId(
                tableFormation.getWithId(match.getFormationEquipeB()).getEquipeId()).getNom());

        TextView scoreEquipeA = (TextView) view.findViewById(R.id.textView_score_equipeA);
        scoreEquipeA.setText(String.valueOf(match.getScoreEquipeA()));

        TextView scoreEquipeB = (TextView) view.findViewById(R.id.textView_score_equipeB);
        scoreEquipeB.setText(String.valueOf(match.getScoreEquipeB()));

        TextView arbitres = (TextView) view.findViewById(R.id.textView_arbitres);
        arbitres.setText(match.getArbitreChamp() + " et " + match.getArbitreAssistant());

        TextView statut = (TextView) view.findViewById(R.id.textView_statut);
        if (match.getResultat() == Match.MATCH_NON_JOUE)
            statut.setText("EN COURS");
        else
            statut.setText("TERMINE");

        Button btnExportComplet = (Button) view.findViewById(R.id.btn_export_complet);
        btnExportComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMatchDAO tableMatch=new DBMatchDAO(mCtx);

                String JSON=tableMatch.MatchJSON(matchId);
                Log.d("ECRITURE","ECRITURE FICHIER");
                Match match = new Match(tableMatch.getWithId(matchId));
                int formationA = match.getFormationEquipeA();
                int formationB = match.getFormationEquipeB();
                DBFormationDAO tableFormation = new DBFormationDAO(mCtx);
                DBEquipeDAO tableEquipe = new DBEquipeDAO(mCtx);
                String nomEquipeA = "";
                String nomEquipeB = "";
                if (formationA != -1)
                    nomEquipeA = tableEquipe.getWithId(tableFormation.getWithId(formationA).getEquipeId()).getNom();
                if (formationB != -1)
                    nomEquipeB = tableEquipe.getWithId(tableFormation.getWithId(formationB).getEquipeId()).getNom();
                tableMatch.exportMatch(match.getDate()+'-'+nomEquipeA+"VS"+nomEquipeB+".json",JSON);


                Toast.makeText(getActivity(),"Export au format JSON",Toast.LENGTH_LONG).show();



                // Export
            }
        });

        Button btnImportComplet = (Button) view.findViewById(R.id.btn_import_complet);
        btnImportComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBMatchDAO tableMatch = new DBMatchDAO(mCtx);
                Match match = new Match(tableMatch.getWithId(matchId));
                int formationA = match.getFormationEquipeA();
                int formationB = match.getFormationEquipeB();
                DBFormationDAO tableFormation = new DBFormationDAO(mCtx);
                DBEquipeDAO tableEquipe = new DBEquipeDAO(mCtx);
                String nomEquipeA = "";
                String nomEquipeB = "";
                if (formationA != -1)
                    nomEquipeA = tableEquipe.getWithId(tableFormation.getWithId(formationA).getEquipeId()).getNom();
                if (formationB != -1)
                    nomEquipeB = tableEquipe.getWithId(tableFormation.getWithId(formationB).getEquipeId()).getNom();

                DBJoueurDAO joueurDAO = new DBJoueurDAO(mCtx);
                List<Joueur> joueursA = new ArrayList<>(joueurDAO.getFormationMatch(formationA));
                List<Joueur> joueursB = new ArrayList<>(joueurDAO.getFormationMatch(formationB));
                DBStatDAO statDAO = new DBStatDAO(mCtx);
                List<Stat> statsA = new ArrayList<>();
                List<Stat> statsB = new ArrayList<>();
                if (!joueursA.isEmpty()){
                    for (Joueur joueur : joueursA)
                        statsA.add(statDAO.getStatFromJoueur(joueur,matchId));
                }
                if (!joueursB.isEmpty()){
                    for (Joueur joueur : joueursB)
                        statsB.add(statDAO.getStatFromJoueur(joueur,matchId));
                }

                String arbitre1=match.getArbitreAssistant(), arbitre2=match.getArbitreChamp();

                try {
                    PdfCreator creator = new PdfCreator();
                    creator.createPdf(Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator + "Android"+File.separator+"data"+File.separator+"BastatsFiles"+File.separator+match.getDate()+'-'+nomEquipeA+"VS"+nomEquipeB+".pdf", nomEquipeA, nomEquipeB, matchId, arbitre1, arbitre2, match.getScoreEquipeA(), match.getScoreEquipeB(), mCtx);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getActivity(),"Export du pdf",Toast.LENGTH_LONG).show();


            }
        });

        return view;
    }
}
