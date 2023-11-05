import React from "react";
import { Card } from "react-bootstrap"
import 'react-toastify/dist/ReactToastify.css';

const DashboardCard = (props: any) => {
    const { title, value } = props;
    return (
        <>
            <Card className="m-3 p-3 rounded" border="secondary" style={{ width: '25%' }}>
                <Card.Body>
                    <div className="">
                        <Card.Text><h2>{value}</h2></Card.Text>
                        <Card.Title><h3>{title}</h3></Card.Title>
                    </div>
                </Card.Body>
            </Card>
        </>
    )
}

export default DashboardCard;