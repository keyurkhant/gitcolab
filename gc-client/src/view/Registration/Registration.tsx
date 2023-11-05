import { Card, Button, Form } from "react-bootstrap";
import "../style.css";
import { useState } from "react";
import { registerUser } from "../../services/UserService";
import { useNavigate } from "react-router";

const Registration = () => {
    const [invalid, setInvalid] = useState(false);
    const [username, setUsername] = useState('');
    const [firstname, setFirstname] = useState('');
    const [lastname, setLastname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMsg, setErrorMsg] = useState('');
    const navigate = useNavigate();

    const handleUsernameChange = (e: any) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
    };

    const handleFirstnameChange = (e: any) => {
        setFirstname(e.target.value);
    };

    const handleLastnameChange = (e: any) => {
        setLastname(e.target.value);
    };

    const handleEmailChange = (e: any) => {
        setEmail(e.target.value);
    };
    
    const handleFormChange = (e: any) => {
        setErrorMsg("");
    };

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity()) {
            const formData = {
                username: username,
                firstName: firstname,
                lastName: lastname,
                email: email,
                password: password
            }
            const response = await registerUser(formData);
            if(response?.data){
                if(response?.status === 200) {
                    navigate('/login');
                } else {
                    setErrorMsg("Something went wrong!");
                }
            } else {
                let message: string = response?.response?.data.message;
                setErrorMsg(message);
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
        }
    };

    return (
        <Card className="auth-card">
            <Card.Header><h1>Registration</h1></Card.Header>
            <Card.Body>
                <Form noValidate validated={invalid} onSubmit={handleSubmit} onChange={handleFormChange}>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="username"
                            type="text"
                            placeholder="Username"
                            value={username}
                            onChange={handleUsernameChange}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter username.
                        </Form.Control.Feedback>
                        <label htmlFor="username">Username</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="firstname"
                            type="text"
                            placeholder="First Name"
                            value={firstname}
                            onChange={handleFirstnameChange}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter Firstname.
                        </Form.Control.Feedback>
                        <label htmlFor="firstname">First name</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="lastname"
                            type="text"
                            placeholder="Last name"
                            value={lastname}
                            onChange={handleLastnameChange}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter Lastname.
                        </Form.Control.Feedback>
                        <label htmlFor="lastname">Last name</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="email"
                            type="email"
                            placeholder="name@example.com"
                            value={email}
                            onChange={handleEmailChange}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter Email Address.
                        </Form.Control.Feedback>
                        <label htmlFor="email">Email address</label>
                    </Form.Floating>
                    <Form.Floating className="mb-3">
                        <Form.Control
                            id="password"
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                        <Form.Control.Feedback type="invalid">
                            Please enter Password.
                        </Form.Control.Feedback>
                        <label htmlFor="password">Password</label>
                    </Form.Floating>
                    {errorMsg !== "" &&
                        <Form.Group className="mt-1" controlId="errorMsg">
                            <Form.Label color="red">{errorMsg}</Form.Label>
                        </Form.Group>
                    }
                    <Button variant="dark" type="submit">Sign up</Button>
                    <Form.Group className="mt-2" controlId="login">
                        <Form.Label><a href="/login">Already user? Sign in.</a></Form.Label>
                    </Form.Group>
                </Form>
            </Card.Body>
        </Card>
    )
}

export default Registration;