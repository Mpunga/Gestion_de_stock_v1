package com.emmanuelapp.gestiondestock.exception;

public enum ErrorCodes {

        ARTICLE_NOT_FOUND(1000),
        ARTICLE_NOT_VALIDE(1001),
        ARTICLE_ALREADY_IN_USE(1002),
        CATEGORY_NOT_FOUND(2000),
        CATEGORY_NOT_VALIDE(2001),
        CATEGORY_ALREADY_IN_USE(2002),

        // TODO complete the rest of the Error Codes
        CLIENT_NOT_FOUND(3000),
        CLIENT_NOT_VALIDE(3001),
        CLIENT_ALREADY_IN_USE(3002),

        COMMANDE_CLIENT_NOT_FOUND(4000),
        COMMANDE_CLIENT_NOT_VALIDE(4001),
        COMMANDE_CLIENT_NON_MODIFIABLE(4002),

        COMMANDE_FOURNISSEUR_NOT_FOUND(5000),
        COMMANDE_FOURNISSEUR_NOT_VALIDE(5001),
        COMMANDE_FOURNISSEUR_NON_MODIFIABLE(5002),
        COMMANDE_FOURNISSEUR_ALREADY_IN_USE(5003),

        ENTREPRISE_NOT_FOUND(6000),
        ENTREPRISE_NOT_VALIDE(60001),

        FOURNISSEUR_NOT_FOUND(7000),
        FOURNISSEUR_NOT_VALIDE(70001),
        FOURNISSEUR_ALREADY_IN_USE(7002),

        LIGNE_COMMANDE_CLIENT_NOT_FOUND(8000),
        LIGNE_COMMANDE_CLIENT_NOT_VALIDE(8001),
        LIGNE_COMMANDE_ALREADY_IN_USE(8002),

        LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(9000),
        LIGNE_COMMANDE_FOURNISSEUR_NOT_VALIDE(9001),
        LIGNE_COMMANDE_FOURNISSEUR_ALREADY_IN_USE(9002),

        LIGNE_VENTE_NOT_FOUND(10000),
        LIGNE_VENTE_NOT_VALIDE(10001),
        MVT_STK_NOT_FOUND(11000),
        MVT_STK_NOT_VALIDE(11001),
        UTILISATEUR_NOT_FOUND(12000),
        UTILISATEUR_NOT_VALIDE(12001),
        UTILISATEUR_ALREADY_EXISTS(12002),
        UTILISATEUR_CHANGE_PASSWORD_OBJECT_NOT_VALID(12003),
        VENTE_NOT_FOUND(13000),
        VENTE_NOT_VALIDE(13001),
        ;
        private int code;

        ErrorCodes(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
}