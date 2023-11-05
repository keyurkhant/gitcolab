import { Card } from "react-bootstrap"
import {Link} from 'react-router-dom';

const Project = ({projectData}:any) => {

    return (
        <Card className="my-3 p-3 rounded " border="primary"  style={{width:'28em'}}>
            {/*- Will change later but clicking each card should re-direct to the dashboard of that project*/}
            <Link to={`/dashboard/${projectData._id}`}>
                <Card.Img src={projectData.image} variant="top"/>
            </Link>  
            
            <Card.Body>
                <Card.Text className="my-1"><h4>{projectData.name}</h4></Card.Text> 
                <Card.Text><strong>{projectData.owner}</strong></Card.Text> 
                <Card.Text as='p'>{projectData.stars} <i className="fas fa-solid fa-star"/>'s</Card.Text> 
                <Card.Text as='p'>{projectData.description}</Card.Text> 
            </Card.Body>

        </Card>
    )
}

export default Project;