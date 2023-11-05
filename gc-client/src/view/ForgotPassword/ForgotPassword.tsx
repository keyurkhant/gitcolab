import { Card, Button, Form } from "react-bootstrap";
import "../style.css";
import { useState } from "react";
import { sendVerificationCode } from "../../services/UserService";
import { useNavigate } from "react-router";

const ForgotPassword = () => {
    const [email, setEmail] = useState('');
    const [invalid, setInvalid] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const navigate = useNavigate();
    const handleEmailChange = (e: any) => {
        setEmail(e.target.value);
    }

    const handleFormChange = () => {
        setErrorMsg('');
    }

    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            const data = { email: email };
            const response = await sendVerificationCode(data);
            if (response?.data) {
                navigate('/forgot-password/otp', {
                    state: {
                        email: email
                    }
                });
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
        <>
            <Card className="auth-card">
                <Card.Header><h1>Forgot Password</h1></Card.Header>
                <Card.Body>
                    <Form noValidate validated={invalid} onSubmit={handleSubmit} onChange={handleFormChange}>
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="floatingInputCustom"
                                type="email"
                                placeholder="name@example.com"
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
                        <Button variant="dark" type="submit">Send Verification Code</Button>
                    </Form>
                </Card.Body>
            </Card>
        </>
    )
}

export default ForgotPassword;