import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import { ToastContainer, toast } from "mdbreact";
import { Button , Form, FormGroup, Label, Input } from 'reactstrap';
import 'font-awesome/css/font-awesome.min.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';
import './GeneradorTabla.css';
import GeneradorTabla from "./GeneradorTabla";




class ListadoEventos extends Component {

    constructor(props) {
        super(props);
        this.submitFormulario = this.submitFormulario.bind(this);
        this.handleError = this.handleError.bind(this);

        this.state ={
            itemsJson: {},
            hasError:false,
            legajoColumnas:[]

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

        // Display the key/value pairs
        for (var pair of data.entries()) {
            console.log(pair[0]+ ', ' + pair[1]);
        }


        var legajo = document.getElementById("legajo").value;
        var fecha_desde_legajo = document.getElementById("fecha_desde_legajo").value;
        var fecha_hasta_legajo = document.getElementById("fecha_hasta_legajo").value;


        var link = ("http://10.80.30.117:8080/obtenerAgentePorFecha/"+legajo+"/"+fecha_desde_legajo+"/"+fecha_hasta_legajo+"");
        const that = this;
        console.log(link);
        fetch(link, {})
            .then(res => res.json())
            .then(res => {
                this.setState({
                    itemsJson: res

                })
            })
            .then(function () {
                console.log("ok");
            }).catch(function () {
                that.handleError();
            });
    }


    render () {
        var legajoColumnas=[{
            Header: 'Ejemplo1',
            columns: [{
                accessor: 'ID',
                Header: 'UO',
                minWidth:95
            },{
                accessor: 'Nombre',
                Header: 'Nombre',
                minWidth:250
            },{
                accessor: 'parametro',
                Header: 'parametro',
                minWidth:70
            },{
                accessor: 'Apellido y Nombre',
                Header: 'Apellido y Nombre',
                minWidth:200
            }]},{
            Header: 'Periodo',
            columns: [{
                accessor: 'Mes',
                Header: 'Mes',
                minWidth:45
            },{
                accessor: 'Año',
                Header: 'Año',
                minWidth:65
            }]},{
            Header: 'Datos del Certificante',
            columns: [{
                accessor: 'Certificado en ID UO',
                Header: 'UO',
                minWidth:95
            },{
                accessor: 'Certificado en Nombre de UO',
                Header: 'Nombre UO',
                minWidth:250
            },{
                accessor: 'Legajo del certificante',
                Header: 'Legajo',
                minWidth:60
            },{
                accessor: 'Nombre del certificante',
                Header: 'Nombre',
                minWidth:200
            },{
                accessor: 'ID',
                Header: 'ID',
                minWidth:75,
                Cell: row => (
                    <div>
                        <a href="/guardarLista">
                            <i className="fas fa-file-download fa-2x"></i><span style={{'font-size': '10px;'}}>Agregar a mi lista</span></a>
                    </div>
                )
            }]
        }];
        console.log((legajoColumnas));

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
                    <br/>
                    <h4 className="font-weight-bold">Consulta</h4>
                    <br/>
                    <Form inline onSubmit={this.submitFormulario} className="col-md-12">
                        <FormGroup className="col-md-2">
                            <Label for="legajo">Numero</Label>
                            <Input type="number" name="legajo" id="legajo" max={999999} />
                        </FormGroup>
                        <FormGroup className="col-md-2">
                            <Label for="fecha_desde_legajo">Fecha desde</Label>
                            <Input type="date" name="fecha_desde_legajo" id="fecha_desde_legajo"/>
                        </FormGroup>
                        <FormGroup className="col-md-2">
                            <Label for="fecha_hasta_legajo">Fecha hasta</Label>
                            <Input type="date" name="fecha_hasta_legajo" id="fecha_hasta_legajo"/>
                        </FormGroup>
                        <FormGroup className="col-md-2">
                            <Label for="egreso" style={{'color': 'white'}}>.</Label>
                            <Button color="#1181b5" className="col-md-12 btn-color">CONSULTAR</Button>
                        </FormGroup>
                    </Form>



                    <Link to='/'><i class="fas mr1 svg-inline--fa fa-arrow-left fa-w-14"></i><span> Volver atrás</span></Link>

                    <div className="justify-content-center">
                        <GeneradorTabla options={this.state.itemsJson} columnas={legajoColumnas}/>
                    </div>
                </div>
            );

    }



}
export default ListadoEventos;