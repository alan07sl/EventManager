import React, { Component } from 'react';
import ReactTable from "react-table";
import 'react-table/react-table.css';



class GeneradorTabla extends Component {
    constructor(props) {
        super(props);
        this.state ={
            isLoaded: false,
            isResult:false
        }

    }
    render(){
        console.log(JSON.stringify(this.props.options));
        if (JSON.stringify(this.props.options)!== JSON.stringify({})){
            return (

                <div className="col-md-12 divCentrado">

                    <br/>
                    <ReactTable
                        data={this.props.options}
                        columns={this.props.columnas}
                        defaultPageSize={10}
                        className={"-striped -highlight"}
                        showPageJump= {true}
                        previousText={'Anterior'}
                        nextText= 'Siguiente'
                        loadingText= 'Loading...'
                        noDataText= 'No hubo resultados'
                        pageText= 'Página'
                        ofText= 'de'
                        rowsText= 'Filas'
                        minRows={1}

                    />
                </div>

            )}else
        {
            return (
                <h5></h5>

            )

        }

    }
}
export default GeneradorTabla;