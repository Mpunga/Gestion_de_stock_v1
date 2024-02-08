package com.emmanuelapp.gestiondestock.utils;

public interface Constants {

    public static String APP_ROOT = "gestiondestock/v1";

    String COMMANDE_FOURNISSEUR_ENDPOINT = APP_ROOT + "/commandesfournisseurs";
    String CREATE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/create";
    String FIND_COMMANDE_FOURNISSEUR_BY_ID_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{idcommandefournisseur}";
    String FIND_COMMANDE_FOURNISSEUR_BY_CODE_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/{codecommandefournisseur}";
    String FIND_ALL_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/all";
    String DELETE_COMMANDE_FOURNISSEUR_ENDPOINT = COMMANDE_FOURNISSEUR_ENDPOINT + "/delete/{idcommandefournisseur}";

    String ENTREPRISE_ENDPOINT = APP_ROOT + "/entreprises";

    String FOURNISSEUR_ENDPOINT = APP_ROOT + "/fourinsseur";

    String UTILISATEUR_ENDPOINT = APP_ROOT + "/utilisateur";

    String VENTES_ENDPOINT = APP_ROOT + "/ventes";
}
