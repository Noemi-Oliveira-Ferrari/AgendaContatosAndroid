package br.senai.sp.catlogodecontatos;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import br.senai.sp.dao.ContatoDAO;
import br.senai.sp.modelo.Contato;

public class CadastroContatoActivity extends AppCompatActivity {

    private CadastroContatoHelper helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_contato);


        /*BOTAO DE VOLTAR A TELA PRINCIPAL*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setTitle("Contatos");

        helper = new CadastroContatoHelper(CadastroContatoActivity.this);


         /*obter intenção*/
         Intent intent = getIntent();

         Contato contato = (Contato) intent.getSerializableExtra("contato");
            if(contato != null) {
                helper.preencherFormulario(contato);
            }

    }



    /*Colocar menu na activity(menu_cadastro_contatos.xmlml)*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    /*Inflador de menu, enche o menu*/
        MenuInflater menuInflater = getMenuInflater();
        /*O método inflate precisa de 2 parâmetros (o layout e o objeto menu)*/
        menuInflater.inflate(R.menu.menu_cadastro_contatos, menu);
        /*Retorna o menu pra quem chamou*/
        return super.onCreateOptionsMenu(menu);
    }


/*Notificação*/
    public void criarNotificacaoSimples(){
        int id = 1;
        String titulo = "Agenda de Contatos";
        String texto = "Um contato foi adicionado à sua lista!";
        int icone = android.R.drawable.ic_dialog_info;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent p = getPendingIntent(id, intent, this);

        NotificationCompat.Builder notificacao = new NotificationCompat.Builder(this);
        notificacao.setSmallIcon(icone);
        notificacao.setContentTitle(titulo);
        notificacao.setContentText(texto);
        notificacao.setContentIntent(p);

        NotificationManagerCompat nm = NotificationManagerCompat.from(this);
        nm.notify(id, notificacao.build());
    }

    private PendingIntent getPendingIntent(int id, Intent intent, Context context){
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(intent.getComponent());
        stackBuilder.addNextIntent(intent);

        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;
    }


    /*Retorna o item selecionado no menu*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      /*getItemId() traz o id do item selecionado*/
       switch (item.getItemId()){
           case R.id.menu_salvar:

                Contato contato = helper.getContato();
            //*** abrir o banco, query de insert, fechar o banco
               ContatoDAO dao = new ContatoDAO(this);

               if(contato.getId() == 0){
                    CadastroContatoHelper helper = new CadastroContatoHelper(this);
                    if(helper.validar() == true){
                        dao.salvar(contato);
                        criarNotificacaoSimples();
                        Toast.makeText(this, contato.getNome() + " gravado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();

                    }else{
                        Toast.makeText(this, "Verifique os dados", Toast.LENGTH_LONG).show();
                    }

               }else{

                   if(helper.validar() == true){
                       dao.atualizar(contato);
                       Toast.makeText(this, contato.getNome() + " atualizado com sucesso!", Toast.LENGTH_LONG).show();
                       finish();

                   }else{
                       Toast.makeText(this, "Verifique os dados", Toast.LENGTH_LONG).show();
                   }

               }

               dao.close();
               break;
           case R.id.menu_del:
               Contato contatoCancelar = helper.getContato();

               if(contatoCancelar.getId() == 0){
                   Toast.makeText(this, "Nenhum contato adicionado!", Toast.LENGTH_LONG).show();
               }else {

                   final ContatoDAO daoExcluir = new ContatoDAO(this);
                   final Contato contatoExcluir = helper.getContato();
                   new AlertDialog.Builder(this).setTitle("Excluir contato").setMessage("Tem certeza que deseja excluir o contato?").setPositiveButton("Sim",
                           new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialogInterface, int i) {
                                   daoExcluir.excluir(contatoExcluir);

                                   Toast.makeText(CadastroContatoActivity.this, contatoExcluir.getNome() + " excluído!", Toast.LENGTH_LONG).show();
                                   daoExcluir.close();
                                   finish();
                               }
                           }).setNegativeButton("Não", null).show();
               }
               break;
           case R.id.menu_ligar:

               Contato contatoLigar = helper.getContato();

               if(contatoLigar.getId() == 0){
                   Toast.makeText(this, "Não é possível ligar!", Toast.LENGTH_LONG).show();
               }else {

                   Contato numeroTelefone = helper.getContato();
                   Uri uri = Uri.parse("tel:" + numeroTelefone.getTelefone());
                   Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                   startActivity(intent);
               }
               break;
               default:
                   Toast.makeText(CadastroContatoActivity.this, "Nada", Toast.LENGTH_LONG).show();
                   break;
           case android.R.id.home:
               /*CHAMADA DA MAIN QUANDO CLICA NO BOTÃO DE VOLTAR*/
               finish();
               break;
       }


        return super.onOptionsItemSelected(item);
    }


}
