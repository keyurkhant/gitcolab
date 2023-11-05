import React from "react";
import { Spinner } from "react-bootstrap";
const CustomSpinner = (props: any) => {

    return (
        <div style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
            <Spinner />
        </div>
    )

}

export default CustomSpinner;