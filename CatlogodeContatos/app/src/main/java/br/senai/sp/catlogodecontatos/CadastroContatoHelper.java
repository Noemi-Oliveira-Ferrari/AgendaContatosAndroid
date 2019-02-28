package br.senai.sp.catlogodecontatos;

import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import br.senai.sp.modelo.Contato;

public class CadastroContatoHelper {

    private EditText txtNome;
    private EditText txtEndereco;
    private EditText txtTelefone;
    private EditText txtEmail;
    private EditText txtEnderecoLinkedin;
    private TextInputLayout layoutTxtNome;
    private TextInputLayout layoutTxtEndereco;
    private TextInputLayout layoutTxtTelefone;
    private TextInputLayout layoutTxtEmail;
    private TextInputLayout layoutTxtEnderecoLinkedin;

    private Contato contato;

    public CadastroContatoHelper(CadastroContatoActivity activity){

         txtNome = activity.findViewById(R.id.txt_nome);
         txtEndereco = activity.findViewById(R.id.txt_endereco);
         txtTelefone = activity.findViewById(R.id.txt_telefone);
         txtEmail = activity.findViewById(R.id.txt_email);
         txtEnderecoLinkedin = activity.findViewById(R.id.txt_endereco_linkedin);
         layoutTxtNome = activity.findViewById(R.id.layout_txt_nome);
         layoutTxtEndereco = activity.findViewById(R.id.layout_txt_endereco);
         layoutTxtTelefone = activity.findViewById(R.id.layout_txt_telefone);
         layoutTxtEmail = activity.findViewById(R.id.layout_txt_email);
         layoutTxtEnderecoLinkedin = activity.findViewById(R.id.layout_txt_endereco_linkedin);
         contato = new Contato();

    }


    public boolean validar(){

        boolean validado = true;
            if(txtNome.getText().toString().isEmpty()){
                layoutTxtNome.setErrorEnabled(true);
                layoutTxtNome.setError("Por favor digite o nome do contato");
                validado = false;
            }
            else{
                layoutTxtNome.setErrorEnabled(false);
            }
            if(txtEndereco.getText().toString().isEmpty()){
                layoutTxtEndereco.setErrorEnabled(true);
                layoutTxtEndereco.setError("Por favor digite o endereço");
                validado = false;
            }
            else{
                layoutTxtEndereco.setErrorEnabled(false);
            }
        if(txtTelefone.getText().toString().isEmpty()){
            layoutTxtTelefone.setErrorEnabled(true);
            layoutTxtTelefone.setError("Por favor digite o telefone");
            validado = false;
        }
        else{
            layoutTxtTelefone.setErrorEnabled(false);
        }
        if(txtEmail.getText().toString().isEmpty()){
            layoutTxtEmail.setErrorEnabled(true);
            layoutTxtEmail.setError("Por favor digite o e-mail");
            validado = false;
        }
        else{
            layoutTxtEmail.setErrorEnabled(false);
        }
        if(txtEnderecoLinkedin.getText().toString().isEmpty()){
            layoutTxtEnderecoLinkedin.setErrorEnabled(true);
            layoutTxtEnderecoLinkedin.setError("Por favor digite o linkedin");
            validado = false;
        }
        else{
            layoutTxtEnderecoLinkedin.setErrorEnabled(false);
        }




        return validado;
    }

    public Contato getContato(){

        contato.setNome(txtNome.getText().toString());
        contato.setEndereco(txtEndereco.getText().toString());
        contato.setTelefone(txtTelefone.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setEndereco_linkedin(txtEnderecoLinkedin.getText().toString());

        return contato;
    }


    public void preencherFormulario(Contato contato) {

        txtNome.setText(contato.getNome());
        txtEndereco.setText(contato.getEndereco());
        txtTelefone.setText(contato.getTelefone());
        txtEmail.setText(contato.getEmail());
        txtEnderecoLinkedin.setText(contato.getEndereco_linkedin());
        this.contato = contato;

    }
}
