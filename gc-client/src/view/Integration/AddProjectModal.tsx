import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { selectUser } from '../../redux/userSlice';
import { createProject } from '../../services/ProjectService';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface AddProjectModalProps {
    projectDetails: ProjectDetails;
    handleClose: () => void;
}

interface ProjectDetails {
    repositoryName: string;
    isAtlassian: boolean;
    jiraBoard?: string;
}

const AdddProjectModal: React.FC<AddProjectModalProps> = ({
    projectDetails,
    handleClose,
}) => {
    const [repositoryName, setRepositoryName] = useState("");
    const [isAtlassian, setIsAtlassian] = useState(false);
    const [jiraBoard, setJiraBoard] = useState("");
    const [invalid, setInvalid] = useState(false);
    const userDataStore = useSelector(selectUser);
    const [errorMsg, setErrorMsg] = useState('');
    const notify = () => toast.success("Project created successfully!");

    const handRepositoryNameChange = (e: any) => {
        setRepositoryName(e.target.value);
    }

    const handleIsAtlassianChange = () => {
        setIsAtlassian(!isAtlassian);
    }

    const handleJiraBoardChange = (e: any) => {
        setJiraBoard(e.target.value);
    }

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true && repositoryName.indexOf(" ") < 0) {
            const projectData = {
                email: userDataStore.email,
                githubToken: userDataStore.githubToken,
                repositoryName: repositoryName,
                atlassianToken: userDataStore.atlassianToken,
                isAtlassianRequired: isAtlassian,
                jiraBoardName: jiraBoard
            }
            if (projectData) {
                const response = await createProject(projectData, userDataStore.token);
                if (response?.data) {
                    if (response?.status === 200) {
                        handleClose();                        
                        notify();
                    } else {
                        setErrorMsg("Something went wrong!");
                    }
                } else {
                    let message: string = response?.response?.data.message;
                    if (message === 'Full authentication is required to access this resource') {
                        setErrorMsg("Atlassian slowdown, try after sometimes!");
                    } else {
                        setErrorMsg(message);
                    }
                }
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
            setErrorMsg("Invalid Repository Name!");
        }
    };

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Project Flow</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="repositoryName"
                            type="text"
                            placeholder="Repository Name"
                            required
                            value={repositoryName}
                            onChange={handRepositoryNameChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid repository name.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Repository Name</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Check
                            type={"checkbox"}
                            id={'addAtlassian'}
                            label={'Want to integrate Atlasssian(JIRA, Confluence)?'}
                            onChange={handleIsAtlassianChange}
                        />
                    </Form.Floating>
                    {isAtlassian && (
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="jiraBoard"
                                type="text"
                                placeholder="JIRA Board Name"
                                value={jiraBoard}
                                onChange={handleJiraBoardChange}
                                required={isAtlassian}
                            />
                            <Form.Control.Feedback type="invalid">
                                Please enter valid JIRA board name.
                            </Form.Control.Feedback>
                            <label htmlFor="floatingInputCustom">JIRA Board Name</label>
                        </Form.Floating>
                    )}
                    {errorMsg !== "" &&
                        <Form.Group className="mt-1" controlId="errorMsg">
                            <Form.Label color="red">{errorMsg}</Form.Label>
                        </Form.Group>
                    }
                    <Button variant="primary" type="submit">
                        Add Project
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AdddProjectModal;
