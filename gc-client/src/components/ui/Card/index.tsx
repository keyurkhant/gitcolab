import React from "react";
import { Card, Button } from "react-bootstrap"
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { sendProjectRequest } from "../../../services/ProjectService";
import { useSelector } from "react-redux";
import { selectUser } from "../../../redux/userSlice";

const CardComponent = (props: any) => {
    const {
        repositoryName,
        repositoryOwner,

    } = props;
    const userDataStore = useSelector(selectUser);
    const successNotify = () => toast.success("Invitation has been sent!");
    const failureNotify = () => toast.error("Invitation can't sent");

    const projectRequestHandler = async () => {
        const response = await sendProjectRequest({projectOwner: repositoryOwner, repositoryName: repositoryName}, userDataStore.token);
        if(response?.status === 200) {            
            successNotify();
        } else {
            failureNotify();
        }
    }

    return (
        <>
            <Card className="m-3 p-3 rounded" border="secondary" style={{ width: '18rem' }}>
                <a href={`https://github.com/${repositoryOwner}`} target="_blank" rel="noopener noreferrer">
                    <Card.Img variant="top" src={`https://avatars.githubusercontent.com/${repositoryOwner}`} alt="Repo Image" />
                </a>
                <Card.Body>
                    <Card.Title><h4>{repositoryName}</h4></Card.Title>
                    <Card.Text className="my-1">{repositoryOwner}</Card.Text>
                    <Button variant="primary" onClick={projectRequestHandler}>Send Request</Button>
                </Card.Body>
            </Card>
        </>
    )
}

export default CardComponent;