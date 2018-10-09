import React, { Component } from 'react';
import './MenuInicio.css';
import {Redirect,Route,Switch,Link} from 'react-router-dom';
import {Button} from 'mdbreact';
import 'font-awesome/css/font-awesome.min.css';
import 'bootstrap-css-only/css/bootstrap.min.css';
import 'mdbreact/dist/css/mdb.css';



class MenuInicio extends Component {

    constructor (props) {
        super(props);
    }

    render (){

        return (

            <div>

                <h2> </h2>
                <br/> <br/>
                <h4 className="divCentrado font-weight-bold">Listados</h4>
                <br/>
                <div className="row col-md-12 divCentrado">
                    <br/>

                    <div className="col-md-6 divCentrado">
                        <Link to="/ListadoEventos"><Button color="#1181b5" className="col-md-6 btn-personal" >Listado de eventos</Button></Link>

                    </div>
                    <div className="col-md-6 divCentrado">
                        <Link to="/MisListas"><Button color="#1181b5" className="btn-personal  col-md-6">Listados propios</Button></Link>
                    </div>
                </div>

            </div>
        )
    }
}



export default MenuInicio;