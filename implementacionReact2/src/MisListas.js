import React, { Component } from 'react';
import { Button , Form, FormGroup, Label, Input } from 'reactstrap';
import GeneradorTabla from "./GeneradorTabla";
import {Link} from 'react-router-dom';
import { ToastContainer, toast } from "mdbreact";
import './GeneradorTabla.css';
import 'font-awesome/css/font-awesome.min.css';


class MisListas extends Component {

    constructor(props) {
        super(props);
        this.submitFormulario = this.submitFormulario.bind(this);
        this.state ={
            isLoaded: false,
            itemsJson: {},
            isResult:false

        }

    }
    handleError= () => {
        this.setState({
            hasError: !this.state.hasError
        });
    };


    submitFormulario(event){
        event.preventDefault();
        const data = new FormData(event.target);

    //this.setState({objectJSON: "hola"});


    // Display the key/value pairs
    for (var pair of data.entries()) {
    console.log(pair[0]+ ', ' + pair[1]);
}


var id_uo_desde = document.getElementById("id_uo_desde").value;
var id_uo_hasta = document.getElementById("id_uo_hasta").value;
var fecha_desde = document.getElementById("fecha_desde").value;
var fecha_hasta = document.getElementById("fecha_hasta").value;


var link = ("http://10.80.30.117:8080/obtenerUOPorFecha/"+id_uo_desde+"/"+id_uo_hasta+"/"+fecha_desde+"/"+fecha_hasta+"");
const that = this;
console.log(link);

fetch(link, {})
    .then (res => res.json())
    .then (res => {
        this.setState({
            isLoaded: true,
            isResult:true,
            itemsJson: res,
        })
    })
    .then(function() {
        console.log("ok");
    }).catch(function() {
    that.handleError();

});
}


    render () {
        var uoColumnas=[{
            Header: 'Datos',
            columns: [{
                accessor: 'ID',
                Header: 'ID',
                minWidth:95
            },{
                accessor: 'Nombre',
                Header: 'Nombre',
                minWidth:250
            }]},{
            Header: 'Periodo',
            columns: [{
                accessor: 'Mes',
                Header: 'Mes',
                minWidth:45
            },{
                accessor: 'Año',
                Header: 'Año',
                minWidth:60
            }]},{
            Header: 'Datos',
            columns: [{
                accessor: 'Legajo',
                Header: 'Legajo',
                minWidth:60
            },{
                accessor: 'Nombre',
                Header: 'Nombre',
                minWidth:200
            },{
                accessor: 'Certificado',
                Header: 'Certificado',
                minWidth:95
            }]},{
            Header: 'Datos',
            columns: [{
                accessor: 'Legajo_del_certificante',
                Header: 'Legajo',
                minWidth:60
            },{
                accessor: 'Nombre_del_certificante',
                Header: 'Nombre',
                minWidth:200
            },{
                accessor: 'Fecha_de_certificación',
                Header: 'Fecha',
                minWidth:70
            },{
                accessor: 'Hora_de_Certificación',
                Header: 'Hora',
                minWidth:60
            },{
                accessor: 'ID',
                Header: 'ID',
                minWidth:65,
            }]
        }];

        let mostrarError;
        const mostrarCartel=this.state.hasError;

        if(mostrarCartel){
            mostrarError = toast.error("Consulta Errónea.Intente Nuevamente.", {
                position: "top-center",
                autoClose: 5000,
                closeButton: false,
            });
            this.handleError();

        }else {
            mostrarError = '';
        }
        return (

            <div class="col-md-12">
                {mostrarError}

                <ToastContainer
                    position="top-center"
                    autoClose={5000}
                    closeButton={false}
                    newestOnTop={false}
                    rtl={false}>
                </ToastContainer>
                <br/>
                <h4 className="font-weight-bold">Consulta mis listas</h4>
                <br/>
                <Form inline onSubmit={this.submitFormulario}>
                    <FormGroup className="col-md-2">
                        <Label for="egreso" style={{'color':'white'}}>.</Label>
                        <Button color="#1181b5" className="col-md-12 btn-color">CONSULTAR</Button>
                    </FormGroup>
                </Form>


                <Link to='/'><i class="fas mr1 svg-inline--fa fa-arrow-left fa-w-14"></i><span> Volver atrás</span></Link>


                <div className="row justify-content-center">
                    <GeneradorTabla options={this.state.itemsJson} columnas={uoColumnas}/>
                </div>
            </div>
        );
    }
}
export default MisListas;