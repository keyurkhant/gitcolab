import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { useSelector } from 'react-redux';
import { selectUser } from '../../redux/userSlice';
import { addCollaborator } from '../../services/ProjectService';
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

interface AddContributorModalProps {
    contributorDetails: ContributorDetails;
    handleClose: () => void;
}

interface ContributorDetails {
    repositoryName: string
}

const AddContributorModal: React.FC<AddContributorModalProps> = ({
    contributorDetails,
    handleClose,
}) => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [errorMsg, setErrorMsg] = useState("");
    const [invalid, setInvalid] = useState(false);
    const userDataStore = useSelector(selectUser);
    const notify = () => toast.success("Invitation has been sent!");

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
    }
    
    const handleEmailChange = (e: any) => {
        setEmail(e.target.value);
    }

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = { username: username, email: email, githubAuthToken: userDataStore.githubToken, repositoryName: contributorDetails.repositoryName};
            if (data) {
                const response = await addCollaborator(data, userDataStore.tokken);
                if(response?.status === 200) {                    
                    handleClose();                    
                    notify();
                } else {
                    setErrorMsg("Something went wrong!");
                }                
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
        }
    };

    return (
        <Modal show={true} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Add Contributor</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="username"
                            type="text"
                            placeholder="Username"
                            required
                            value={username}
                            onChange={handleUsernameChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid username.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Github Username</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="email"
                            type="email"
                            placeholder="Email"
                            required
                            value={email}
                            onChange={handleEmailChange}
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter valid email.
                        </Form.Control.Feedback>
                        <label htmlFor="floatingInputCustom">Email</label>
                    </Form.Floating>
                    {errorMsg !== "" &&
                        <Form.Group className="mt-1" controlId="errorMsg">
                            <Form.Label color="red">{errorMsg}</Form.Label>
                        </Form.Group>
                    }
                    <Button variant="primary" type="submit">
                        Grant Access
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AddContributorModal;
