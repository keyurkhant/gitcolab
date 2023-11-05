import { Card, Button, Form } from "react-bootstrap";
import "../style.css";
import { useState } from "react";
import { validateVerificationCode } from "../../services/UserService";
import { useLocation, useNavigate } from "react-router";

const Otp = () => {
    const [invalid, setInvalid] = useState(false);
    const [errorMsg, setErrorMsg] = useState('');
    const [verificationCode, setVerificationCode] = useState("");
    const navigate = useNavigate();
    const { state } = useLocation();
    const handleVerificationCodeChange = (e: any) => {
        setVerificationCode(e.target.value);
    }
    const handleSubmit = async (event: any) => {
        const form = event.currentTarget;
        event.preventDefault();
        if (form.checkValidity() === true) {
            if (!state) {
                event.stopPropagation();
                setErrorMsg('Something went wrong!');
            } else {
                const data = { email: state.email, otp: verificationCode };
                const response = await validateVerificationCode(data);
                if (response?.data) {
                    navigate('/reset-password', {
                        state: {
                            email: state.email
                        }
                    });
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
                <Card.Header><h1>User Verification</h1></Card.Header>
                <Card.Body>
                    <Form noValidate validated={invalid} onSubmit={handleSubmit} onChange={handleFormChange}>
                        <Form.Floating className="mb-3">
                            <Form.Control
                                id="floatingInputCustom"
                                type="text"
                                placeholder="Verification Code"
                                required
                                value={verificationCode}
                                onChange={handleVerificationCodeChange}
                            />
                            <Form.Control.Feedback type="invalid">
                                Please enter valid verification code.
                            </Form.Control.Feedback>
                            <label htmlFor="floatingInputCustom">Verification code</label>
                        </Form.Floating>
                        {errorMsg !== "" &&
                            <Form.Group className="mt-1" controlId="errorMsg">
                                <Form.Label color="red">{errorMsg}</Form.Label>
                            </Form.Group>
                        }
                        <Button variant="dark" type="submit">Submit</Button>
                    </Form>
                </Card.Body>
            </Card>
        </>
    )
}

export default Otp;