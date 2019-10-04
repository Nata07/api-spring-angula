import { OportunidadeService } from './../oportunidade.service';
import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-painel-negociacao',
  templateUrl: './painel-negociacao.component.html',
  styleUrls: ['./painel-negociacao.component.css']
})

export class PainelNegociacaoComponent implements OnInit {

  oportunidade = {};
  oportunidades = []; 

  constructor(private opService: OportunidadeService, 
              private message : MessageService) { }

  ngOnInit() {  
    this.consultar();
   
  }

  consultar(){
    this.opService.listar()
      .subscribe(resposta => this.oportunidades = <any> resposta);

  }

  salvar(){
    this.opService.salvar(this.oportunidade)
      .subscribe(() => {
        this.oportunidade = {};
        this.consultar();

        this.message.add({
          severity: 'success',
          summary: "Oportunidade adicionada com sucesso!"
        });
      },
      //quando der erro no subscribe
      resposta => {
        let msg = 'erro inesperado';
        //pegar mensagem padrao da api
        if(resposta.error.message){
          msg = resposta.error.message;
        }
        this.message.add({
          severity: 'error',
          summary: msg
        });

      });

  }

}
