import { Card, Button, Form } from "react-bootstrap";
import "../style.css";
import { useState } from "react";
import { useLocation, useNavigate } from "react-router";
import { resetPassword } from "../../services/UserService";

const ResetPassword = () => {
    const [invalid, setInvalid] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const { state } = useLocation();
    const navigate = useNavigate();
    
    const handlePasswordChange = (e: any) => {
        setPassword(e.target.value);
    }
    const handleConfirmPasswordChange = (e: any) => {
        setConfirmPassword(e.target.value);
    }
    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            if (!state) {
                event.stopPropagation();
                setErrorMsg('Something went wrong!');
            } else {
                const data = { email: state.email, password: password, confirmPassword: confirmPassword };
                const response = await resetPassword(data);
                if (response?.data) {
                    navigate('/login');
                } else {
                    let message: string = response?.response?.data.message;
                    setErrorMsg(message);
                }
            }
        } else {
            event.stopPropagation();
            setInvalid(true);
        }
    }

    const handleFormChange = () => {
        setErrorMsg('');
    }
    return (
        <>
            <Card className="auth-card">
                <Card.Header><h1>Reset Password</h1></Card.Header>
                <Card.Body>
                    <Form noValidate validated={invalid} onSubmit={handleSubmit} onChange={handleFormChange}>
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="floatingInputCustom"
                                type="password"
                                placeholder="Password"
                                required
                                value={password}
                                onChange={handlePasswordChange}
                            />
                            <Form.Control.Feedback type="invalid">
                                Please enter valid password.
                            </Form.Control.Feedback>
                            <label htmlFor="floatingInputCustom">Password</label>
                        </Form.Floating>
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="floatingInputCustom"
                                type="text"
                                placeholder="Confirm Password"
                                required
                                value={confirmPassword}
                                onChange={handleConfirmPasswordChange}
                            />
                            <Form.Control.Feedback type="invalid">
                                Please enter valid password.
                            </Form.Control.Feedback>
                            <label htmlFor="floatingInputCustom">Confirm Password</label>
                        </Form.Floating>
                        {errorMsg !== "" &&
                            <Form.Group className="mt-1" controlId="errorMsg">
                                <Form.Label color="red">{errorMsg}</Form.Label>
                            </Form.Group>
                        }
                        <Button variant="dark" type="submit">Change Password</Button>
                    </Form>
                </Card.Body>
            </Card>
        </>
    )
}

export default ResetPassword;