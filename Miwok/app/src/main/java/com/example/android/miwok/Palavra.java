package com.example.android.miwok;

/**
 * Classe que armazena uma unidade de cada categoria
 */
public class Palavra {

    //Armazena a tradução em português
    private String mTraducaoPadrao;
    //Armazena a tradução em miwok
    private String mTraducaoMiwok;
    //Armazena o id da imagem correspondente
    private int mReferenciaImagem = SEM_IMAGEM_FORNECIDA;
    //Armazena o id do audio correspondente
    private int mReferenciaAudio;
    //Verifica se há uma imagem
    private static final int SEM_IMAGEM_FORNECIDA = -1;


    /**Retorna true se tiver imagem
     * @return - verdadeiro ou falso
     */
    public boolean hasImagem(){
        return mReferenciaImagem != SEM_IMAGEM_FORNECIDA;
    }

    /**Contrutor da classe palavra
     * @param traducaoPadrao - palavra em português
     * @param traducaoMiwok - palavra em miwok
     * @param referenciaImagem - referência da imagem
     * @param referenciaAudio - referência do áudio
     * */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaImagem, int referenciaAudio){
        mTraducaoMiwok = traducaoMiwok;
        mTraducaoPadrao = traducaoPadrao;
        mReferenciaImagem = referenciaImagem;
        mReferenciaAudio = referenciaAudio;
    }

    /**Contrutor da classe palavra
     * @param traducaoPadrao - palavra em português
     * @param traducaoMiwok - palavra em miwok
     * @param referenciaAudio - referência do áudio
     * */
    public Palavra(String traducaoPadrao, String traducaoMiwok, int referenciaAudio){
        mTraducaoMiwok = traducaoMiwok;
        mTraducaoPadrao = traducaoPadrao;
        mReferenciaAudio = referenciaAudio;
    }

    /**retorna a palavra em português
     * @return - mTraducaoPadrao
     */
    public String getTraducaoPadrao() {
        return mTraducaoPadrao;
    }

    /**retorna uma referencia do audio
     * @return - mReferenciaAudio
     */
    public int getReferenciaAudio() {
        return mReferenciaAudio;
    }

    /**retorna a palavra em miwok
     * @return - mTraducaoMiwok
     */
    public String getTraducaoMiwok() {
        return mTraducaoMiwok;
    }

    /**retorna uma referencia da imagem
     * @return - mReferenciaImagem
     */
    public int getReferenciaImagem(){
        return mReferenciaImagem;
    }
}
